# Java Package 루트 등록 방법

## 1. Package 선언

Java에서 패키지를 사용하려면 소스 파일의 첫 번째 줄에 `package` 키워드를 사용하여 패키지를 선언합니다.

```java
package com.example.myapp;

public class MyClass {
    // 클래스 내용
}
```

## 2. 디렉토리 구조

패키지 선언과 디렉토리 구조는 반드시 일치해야 합니다.

```
프로젝트 루트/
├── src/
│   └── com/
│       └── example/
│           └── myapp/
│               └── MyClass.java
└── bin/
```

## 3. 클래스패스(Classpath) 설정

### 3.1 컴파일 시 클래스패스 설정

```bash
# 소스 루트를 클래스패스로 지정
javac -cp src src/com/example/myapp/MyClass.java

# 또는 현재 디렉토리를 클래스패스로 지정
javac -cp . com/example/myapp/MyClass.java
```

### 3.2 실행 시 클래스패스 설정

```bash
# 컴파일된 클래스가 있는 루트 디렉토리를 지정
java -cp bin com.example.myapp.MyClass

# 또는 현재 디렉토리에서 실행
java -cp . com.example.myapp.MyClass
```

## 4. IDE에서의 패키지 루트 설정

### 4.1 Eclipse
1. 프로젝트 우클릭 → Properties
2. Java Build Path → Source 탭
3. Add Folder로 소스 루트 디렉토리 추가

### 4.2 IntelliJ IDEA
1. File → Project Structure
2. Modules → Sources 탭
3. Mark as: Sources로 루트 디렉토리 지정

### 4.3 VS Code
1. `.vscode/settings.json` 파일 생성
2. 다음 설정 추가:
```json
{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin"
}
```

## 5. Maven/Gradle 프로젝트에서의 패키지 루트

### 5.1 Maven 표준 디렉토리 구조
```
프로젝트/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── example/
│   │               └── myapp/
│   └── test/
│       └── java/
└── pom.xml
```

### 5.2 Gradle 표준 디렉토리 구조
```
프로젝트/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── example/
│   │               └── myapp/
│   └── test/
│       └── java/
└── build.gradle
```

## 6. 환경변수 CLASSPATH 설정

### 6.1 Windows
```cmd
set CLASSPATH=C:\MyProject\src;C:\MyProject\lib\*
```

### 6.2 Linux/Mac
```bash
export CLASSPATH=/home/user/MyProject/src:/home/user/MyProject/lib/*
```

## 7. JAR 파일에서의 패키지 루트

JAR 파일을 생성할 때도 패키지 구조가 유지됩니다:

```bash
# JAR 파일 생성
jar cvf myapp.jar -C bin .

# JAR 파일 실행
java -cp myapp.jar com.example.myapp.MyClass
```

## 8. 주의사항

1. **패키지명과 디렉토리 구조 일치**: 패키지 선언과 실제 디렉토리 구조가 정확히 일치해야 합니다.
2. **소문자 사용**: 패키지명은 모두 소문자로 작성하는 것이 관례입니다.
3. **예약어 피하기**: Java 예약어를 패키지명으로 사용하지 않습니다.
4. **역도메인 명명**: 일반적으로 `com.회사명.프로젝트명` 형태를 사용합니다.

## 9. 예제

```java
// src/com/example/utils/StringHelper.java
package com.example.utils;

public class StringHelper {
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
```

```java
// src/com/example/main/Application.java
package com.example.main;

import com.example.utils.StringHelper;

public class Application {
    public static void main(String[] args) {
        String result = StringHelper.capitalize("hello");
        System.out.println(result); // Hello
    }
}
```

컴파일 및 실행:
```bash
javac -cp src src/com/example/utils/StringHelper.java
javac -cp src src/com/example/main/Application.java
java -cp src com.example.main.Application
```