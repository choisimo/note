# 프로젝트 구조 & 빌드 도구 가이드

## 1. 표준 디렉터리 구조
### (1) Maven / Gradle 공통 (자바 기본)
```
project-root/
 ├─ src/
 │   ├─ main/
 │   │   ├─ java/              # 애플리케이션 소스
 │   │   ├─ resources/         # 설정(yaml, properties), 정적 리소스
 │   │   └─ webapp/ (선택)     # JSP, web.xml (Servlet 기반)
 │   └─ test/
 │       ├─ java/              # 테스트 코드
 │       └─ resources/         # 테스트 리소스
 ├─ build.gradle / pom.xml
 ├─ gradlew / mvnw              # Wrapper 스크립트
 ├─ README.md
 └─ docs/
```

### (2) 다중 모듈 (멀티 프로젝트)
Gradle:
```
settings.gradle
root-project/
 ├─ build.gradle
 ├─ module-api/
 │   └─ build.gradle
 ├─ module-core/
 │   └─ build.gradle
 └─ module-batch/
     └─ build.gradle
```
Maven:
```
pom.xml (packaging=pom)
 ├─ api/pom.xml
 ├─ core/pom.xml
 └─ batch/pom.xml
```

## 2. 패키지(네이밍) 전략
| 전략 | 설명 | 예 | 비고 |
|------|------|----|------|
| 도메인 기반 | 비즈니스 하위 도메인 단위 | `com.example.billing.invoice` | 확장 용이 |
| 계층 기반 | 기술 레이어 중심 | `controller`, `service`, `repository` | 단순하나 복잡성 증가 시 혼선 |
| 혼합(추천) | 상위는 도메인, 하위에 계층 | `com.example.order.app`, `domain`, `infra` | 의존 방향 명확 |
| 모듈 분할 | JPMS 또는 멀티모듈 | `com.example.payment.api` | 강한 캡슐화 |

### 패키지 규칙 (권장)
1. 상위 2~3단계: 조직 + 제품 + 도메인 (`com.company.product.order`)
2. 하위: `domain`, `app`(use-case), `infra`(외부 연동), `presentation`(웹/UI)
3. 엔티티/Value Object는 `domain` 패키지에서만 공개.
4. 순환 의존 금지: `app` → `domain` 단방향, `infra`는 구현체로 `domain`의 인터페이스를 구현.

## 3. 의존 방향(헥사고날/클린 아키텍처 요약)
```
[presentation] → [app(use-case)] → [domain] ← [infra(adapters)]
```
- Domain: 순수 비즈니스 규칙(엔티티, Value Object, Repository Port 인터페이스)
- App: 유스케이스(서비스). 트랜잭션, 워크플로, 도메인 조합
- Presentation: HTTP/CLI/API 어댑터 (Controller)
- Infra: DB/JPA 구현, 메시징, 외부 API, File IO

## 4. Gradle vs Maven 선택 가이드
| 항목 | Maven | Gradle |
|------|-------|--------|
| 학습 곡선 | 낮음 | 중간 |
| 선언 방식 | XML | Groovy/Kotlin DSL |
| 확장성 | 플러그인 많음 | 플러그인 + 커스터마이징 우수 |
| 빌드 속도 | 비교적 느림 | Incremental, Build Cache 빠름 |
| 멀티모듈 구성 | 명시적/안정 | 대규모 유연 |
| 의존 버전 관리 | BOM 우수 | Version Catalog (`libs.versions.toml`) |

## 5. Gradle 기본 예시 (Kotlin DSL)
`settings.gradle.kts`
```kotlin
rootProject.name = "sample-app"
include("core", "api")
```
`build.gradle.kts`
```kotlin
plugins { java }
java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }
repositories { mavenCentral() }
dependencies {
  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}
tasks.test { useJUnitPlatform() }
```

## 6. Maven 기본 예시
```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>sample-app</artifactId>
  <version>1.0.0</version>
  <properties>
    <maven.compiler.release>17</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>
  <dependencies>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>5.10.0</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.2.5</version>
      </plugin>
    </plugins>
  </build>
</project>
```

## 7. 계층 & 모듈 경계에서 발생하는 흔한 문제
| 문제 | 원인 | 해결 |
|------|------|------|
| 서비스 간 순환 호출 | 도메인/유스케이스 혼합 | 유스케이스 분리, 조정자 도입 |
| 엔티티 누수 | 엔티티를 직접 Controller 응답에 사용 | DTO/Assembler 패턴 활용 |
| Infra 의존 침투 | JDBC 객체가 서비스/도메인에 존재 | Repository 인터페이스 추상화 |
| 거대 서비스 클래스 | 유스케이스 다중 혼재 | 단일 책임 기준 분리 |

## 8. 정적 분석 & 품질
- Gradle: `./gradlew check` (SpotBugs, PMD, Checkstyle 연동)
- Maven: `mvn verify`
예: SpotBugs(Gradle)
```kotlin
plugins { id("com.github.spotbugs") version "5.2.5" }
spotbugs { toolVersion.set("4.8.3") }
```

## 9. 설정 외부화
- `application.properties|yaml` → Spring 사용 시 권장
- 순수 Java: `config/` 디렉터리 + 클래스패스 로드 또는 `-Dapp.env=prod`
- 보안 비밀: `.env` + 실행 시 주입(환경변수), 저장소 커밋 금지

## 10. 빌드 성능 팁
| 구분 | 방법 |
|------|------|
| Gradle | Build Cache, Configuration Cache, 병렬 빌드 `--parallel` |
| Maven | `mvn -T 1C` (코어 당 1스레드), incremental compiler |
| 공통 | JDK 17+, 의존 최소화, CI 레이어 캐시 활용 |

## 11. Reproducible Build
- Maven: `maven-jar-plugin` `reproducible` 지원 (플러그인 최신 버전)
- Gradle: 기본 reproducible JAR (파일 타임스탬프 정규화)
- Docker 사용 시 `--no-cache` 회피 후 `buildkit` 캐시 활용

## 12. 체크리스트
- [ ] Java Toolchain(17 이상) 통일
- [ ] 멀티모듈 시 공통 parent/platform 정의
- [ ] 품질 플러그인(SpotBugs/Checkstyle) 적용
- [ ] 테스트 플랫폼(JUnit 5) 통일
- [ ] Reproducible build 옵션 확인
- [ ] CI에 build + test + report 파이프라인 구성

---
필요 시 Spring Boot Layered JAR 및 Docker 멀티스테이지 예제 확장 가능.
