# Manifest.MF & JAR 패키징 가이드

## 1. 기본 개념
- `JAR (Java ARchive)`: 여러 `.class`, 리소스(이미지, properties), 메타데이터(Manifest)를 ZIP 형태로 묶은 표준 배포 단위.
- `Manifest.MF`: JAR 내부 `META-INF/MANIFEST.MF` 경로에 존재하는 메타데이터 파일. 메인 클래스 지정, 클래스패스, 버전 정보 등을 기술.
- Java 9+ 모듈 시스템(JPMS) 사용 시 `module-info.java` 로 추가적 메타데이터/캡슐화 제공.

## 2. 수동 컴파일 & 실행 (기본 흐름)
```bash
# 1) 소스 컴파일
javac -d out $(find src -name "*.java")

# 2) 메인 클래스 확인 (예: com.example.app.Main)
# 3) Manifest 파일 작성
cat > manifest.mf <<EOF
Manifest-Version: 1.0
Main-Class: com.example.app.Main
Implementation-Title: Sample CLI
Implementation-Version: 1.0.0
Created-By: 17 (Temurin)
EOF

# 4) JAR 생성
jar cfm app.jar manifest.mf -C out .

# 5) 실행
java -jar app.jar
```

### Manifest 필드 주요 항목
| 필드 | 의미 | 비고 |
|------|------|------|
| Manifest-Version | 스펙 버전 | 거의 1.0 고정 |
| Main-Class | `public static void main` 포함 클래스 | 마지막에 개행必 |
| Class-Path | 런타임 필요 외부 JAR 경로 | 공백 구분, 상대경로 가능 |
| Implementation-Title | 구현 이름 | 로깅/모니터링 식별 |
| Implementation-Version | 구현 버전 | 배포 추적 |
| Implementation-Vendor | 공급자 | 조직/회사명 |
| Built-By | 빌드 사용자 | 선택 |

`Main-Class` 라인 끝에는 반드시 줄바꿈 필요. 누락 시 실행 오류.

## 3. Class-Path 활용 예
```
Class-Path: lib/slf4j-api-2.0.9.jar lib/logback-classic-1.4.14.jar
```
JAR 구조 예:
```
app.jar
lib/
  slf4j-api-2.0.9.jar
  logback-classic-1.4.14.jar
```
실행: `java -jar app.jar`
(Manifest의 Class-Path 덕분에 별도 -cp 지정 불필요)

## 4. 다중 모듈/패키징 (Maven & Gradle)
### Maven
`pom.xml`의 `<build><plugins>` 에 `maven-jar-plugin` 또는 실행형은 `maven-shade-plugin`.
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-jar-plugin</artifactId>
  <version>3.3.0</version>
  <configuration>
    <archive>
      <manifest>
        <mainClass>com.example.app.Main</mainClass>
        <addDefaultImplementationEntries>true</addDefaultImplementationEntries>
      </manifest>
    </archive>
  </configuration>
</plugin>
```
Fat/uber JAR (의존 포함) 필요 시 Shade:
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-shade-plugin</artifactId>
  <version>3.5.0</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals><goal>shade</goal></goals>
      <configuration>
        <transformers>
          <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
            <mainClass>com.example.app.Main</mainClass>
          </transformer>
        </transformers>
      </configuration>
    </execution>
  </executions>
</plugin>
```

### Gradle (Groovy DSL)
```groovy
plugins {
  id 'java'
  id 'application'
}

application {
  mainClass = 'com.example.app.Main'
}

jar {
  manifest {
    attributes(
      'Main-Class': application.mainClass,
      'Implementation-Title': 'Sample CLI',
      'Implementation-Version': version
    )
  }
}

// Shadow plugin (fat jar)
plugins { id 'com.github.johnrengelman.shadow' version '8.1.1' }
```
실행: `./gradlew run` 또는 `./gradlew shadowJar` 후 `java -jar build/libs/app-all.jar`

## 5. Java 9+ 모듈 시스템(JPMS) 간단 예
```
src/com.example.app/module-info.java
module com.example.app {
  requires java.logging;
  exports com.example.app.api;
}
```
모듈 JAR 컴파일 & 실행:
```bash
javac -d out $(find src -name "*.java")
jar --create --file app.jar --main-class com.example.app.Main -C out .
java -p app.jar -m com.example.app
```

## 6. 버전/빌드 메타데이터 주입
- Maven: `maven-jar-plugin` `addDefaultImplementationEntries` → `Implementation-Version`, `Built-By` 자동.
- Gradle: `attributes 'Implementation-Version': project.version`
- Git commit 해시 포함 예 (Gradle):
```groovy
def gitHash = 'git rev-parse --short HEAD'.execute().text.trim()
jar { manifest { attributes 'Implementation-Commit': gitHash } }
```

## 7. 실행 최적화 옵션
| 목적 | 예시 옵션 | 설명 |
|------|-----------|------|
| 메모리 제한 | `-Xms256m -Xmx512m` | 힙 사이즈 고정 |
| GC 튜닝 | `-XX:+UseG1GC` | 서버 애플리케이션 기본 추천 |
| 모듈 단순화 | `--add-opens` | 리플렉션 접근 허용 |
| 시작속도 | `-XX:TieredStopAtLevel=1` | 초기 JIT 단계 축소 |

## 8. 흔한 문제 & 해결
| 문제 | 원인 | 해결 |
|------|------|------|
| `no main manifest attribute` | Manifest에 `Main-Class` 누락/개행 없음 | Manifest 재작성 후 재패키징 |
| `ClassNotFoundException` | Class-Path 또는 패키지 경로 불일치 | compiled output 구조/패키지명 점검 |
| `Unsupported major.minor version` | JDK/JRE 버전 불일치 | `--release` 옵션 or 동일 JDK 배포 |
| Fat JAR 충돌 (NoSuchMethodError) | 다중 버전 라이브러리 병합 | Shade relocation/의존 정리 |

## 9. 베스트 프랙티스 요약
1. 실행형 JAR: 가능한 빌드 도구 사용(재현성, 의존 관리).
2. Fat JAR는 필요할 때만(보안 패치/업데이트 부담 증가).
3. Manifest 필드는 최소 `Main-Class`, `Implementation-Version` 정도는 유지.
4. reproducible build: Maven/Gradle reproducible 옵션 활성화.
5. JDK 17 LTS 이상 사용 권장.

## 10. 추가 학습 경로
- JEP 261 (Module System)
- Reproducible Builds for Maven/Gradle
- JAR Signing (`jarsigner`) & 보안 배포

---
필요 시 Spring Boot / Native Image(GraalVM) 패키징 비교 문서도 추가 가능합니다.
