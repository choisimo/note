
## 1. 예제 프로젝트 정보

- **프로젝트 이름**: my-gradle-app
- **패키지 이름**: com.example.hello

---

## 2. 올바른 디렉터리 구조

```
my-gradle-app/
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── example/
    │               └── hello/
    │                   └── Main.java
    └── test/
        └── java/
            └── com/
                └── example/
                    └── hello/
                        └── MainTest.java
```

---

## 3. 각 파일/폴더 설명

- **src/main/java/com/example/hello/Main.java**  
  → 실제 애플리케이션 코드  
  ```java
  package com.example.hello;

  public class Main {
      public static void main(String[] args) {
          System.out.println("Hello, Gradle!");
      }
  }
  ```

- **src/test/java/com/example/hello/MainTest.java**  
  → 테스트 코드  
  ```java
  package com.example.hello;

  import org.junit.jupiter.api.Test;
  import static org.junit.jupiter.api.Assertions.*;

  class MainTest {
      @Test
      void testMain() {
          assertEquals(2, 1 + 1);
      }
  }
  ```

---

## 4. 잘못된 구조 예시 (비추천)

```
my-gradle-app/
└── src/
    └── main/
        └── java/
            └── main/
                └── java/
                    └── com/
                        └── example/
                            └── hello/
                                └── Main.java
```
- 이런 구조는 **패키지 선언이 `main.java.com.example.hello`**가 되어야 하므로, 일반적으로 잘못된 구조입니다.

---

## 5. 요약

- **src/main/java/패키지명/클래스.java** 형태로 구성
- 패키지명과 폴더 구조가 반드시 일치해야 함
- Gradle Wrapper 파일들은 프로젝트 루트와 `gradle/wrapper/`에 위치

---

