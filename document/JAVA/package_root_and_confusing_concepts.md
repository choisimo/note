# Java 패키지 루트 설정 가이드

## 1. 패키지 루트(Source Root) 설정

### 1.1 패키지 루트란?

패키지 루트는 Java 소스 코드의 최상위 디렉토리로, 패키지 구조가 시작되는 지점입니다.

```
프로젝트/
├── src/                    ← 패키지 루트
│   └── com/
│       └── example/
│           └── app/
│               └── Main.java
└── lib/
```

### 1.2 명령줄에서 패키지 루트 설정

```bash
# 컴파일 시 소스 루트 지정
javac -sourcepath src -d bin src/com/example/app/Main.java

# 실행 시 클래스 루트 지정
java -cp bin com.example.app.Main

# 여러 루트 디렉토리 지정 (구분자: Windows ;, Linux/Mac :)
javac -cp "lib/*:src" -d bin src/com/example/app/Main.java
```

### 1.3 Eclipse에서 소스 루트 설정

1. 프로젝트 우클릭 → **Build Path** → **Configure Build Path**
2. **Source** 탭 선택
3. **Add Folder** 클릭하여 소스 루트 추가
4. **Remove**로 불필요한 루트 제거

```
예시:
✓ src/main/java     (메인 소스 루트)
✓ src/test/java     (테스트 소스 루트)
✓ src/main/resources (리소스 루트)
```

### 1.4 IntelliJ IDEA에서 소스 루트 설정

1. **File** → **Project Structure** (Ctrl+Alt+Shift+S)
2. **Modules** → **Sources** 탭
3. 디렉토리 선택 후:
   - **Mark as: Sources** (파란색) - 일반 소스
   - **Mark as: Test Sources** (초록색) - 테스트 소스
   - **Mark as: Resources** (보라색) - 리소스 파일
   - **Mark as: Excluded** (빨간색) - 제외

### 1.5 VS Code에서 소스 루트 설정

`.vscode/settings.json` 파일 생성:

```json
{
    "java.project.sourcePaths": [
        "src/main/java",
        "src/main/resources"
    ],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}
```

### 1.6 Maven 프로젝트 소스 루트

```xml
<!-- pom.xml -->
<build>
    <sourceDirectory>src/main/java</sourceDirectory>
    <testSourceDirectory>src/test/java</testSourceDirectory>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
        </resource>
    </resources>
</build>
```

### 1.7 Gradle 프로젝트 소스 루트

```gradle
// build.gradle
sourceSets {
    main {
        java {
            srcDirs = ['src/main/java', 'src/custom/java']
        }
        resources {
            srcDirs = ['src/main/resources']
        }
    }
    test {
        java {
            srcDirs = ['src/test/java']
        }
    }
}
```

---

## 2. Class vs Record - 헷갈리기 쉬운 구분

### 2.1 Class (일반 클래스)

```java
// 전통적인 데이터 클래스
public class Person {
    private final String name;
    private final int age;
    
    // 생성자
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Getter
    public String getName() { return name; }
    public int getAge() { return age; }
    
    // equals, hashCode, toString 수동 구현 필요
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age && name.equals(person.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + '}';
    }
}
```

### 2.2 Record (Java 16+)

```java
// Record로 간결하게 표현
public record Person(String name, int age) {
    // 생성자, getter, equals, hashCode, toString 자동 생성
}

// 사용법
Person p = new Person("홍길동", 30);
System.out.println(p.name());      // getter는 getName()이 아닌 name()
System.out.println(p.age());
System.out.println(p);             // Person[name=홍길동, age=30]
```

### 2.3 주요 차이점 비교표

| 특성 | Class | Record |
|------|-------|--------|
| **가변성** | 가변/불변 모두 가능 | **항상 불변(immutable)** |
| **상속** | 다른 클래스 상속 가능 | **상속 불가** (final) |
| **필드** | 자유롭게 추가 가능 | **생성자 매개변수만** |
| **Getter** | `getName()` 형식 | **`name()` 형식** |
| **Setter** | 추가 가능 | **불가능** |
| **equals/hashCode** | 수동 구현 필요 | **자동 생성** |
| **toString** | 수동 구현 필요 | **자동 생성** |
| **용도** | 복잡한 비즈니스 로직 | **데이터 전달용(DTO)** |

### 2.4 Record 고급 기능

```java
public record Point(int x, int y) {
    
    // Compact 생성자 (유효성 검사)
    public Point {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("좌표는 음수일 수 없습니다");
        }
    }
    
    // 추가 생성자
    public Point() {
        this(0, 0);
    }
    
    // 정적 메서드
    public static Point origin() {
        return new Point(0, 0);
    }
    
    // 인스턴스 메서드
    public double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }
    
    // toString 오버라이드
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
```

---

## 3. 헷갈리기 쉬운 Java 개념들

### 3.1 Interface vs Abstract Class

| 특성 | Interface | Abstract Class |
|------|-----------|----------------|
| **다중 상속** | ✅ 여러 개 구현 가능 | ❌ 하나만 상속 |
| **필드** | `public static final`만 | 모든 접근 제한자 가능 |
| **메서드** | default/static만 구현 가능 | 구현/추상 모두 가능 |
| **생성자** | ❌ 없음 | ✅ 있음 |
| **용도** | 계약(contract) 정의 | 공통 기능 제공 |

```java
// Interface
interface Drawable {
    void draw();  // 자동으로 public abstract
    
    default void print() {  // Java 8+
        System.out.println("Printing...");
    }
}

// Abstract Class
abstract class Shape {
    protected String color;  // 인스턴스 필드 가능
    
    public Shape(String color) {  // 생성자 가능
        this.color = color;
    }
    
    abstract void draw();  // 추상 메서드
    
    void display() {  // 구현된 메서드
        System.out.println("Color: " + color);
    }
}
```

### 3.2 == vs equals()

```java
// == : 참조(주소) 비교
String s1 = new String("Hello");
String s2 = new String("Hello");
System.out.println(s1 == s2);      // false (다른 객체)

// equals() : 값 비교
System.out.println(s1.equals(s2)); // true (내용이 같음)

// String literal pool (특수 케이스)
String s3 = "Hello";
String s4 = "Hello";
System.out.println(s3 == s4);      // true (같은 객체)
```

### 3.3 Overloading vs Overriding

```java
// Overloading (오버로딩) - 같은 이름, 다른 매개변수
class Calculator {
    int add(int a, int b) { return a + b; }
    double add(double a, double b) { return a + b; }
    int add(int a, int b, int c) { return a + b + c; }
}

// Overriding (오버라이딩) - 부모 메서드 재정의
class Animal {
    void sound() { System.out.println("동물 소리"); }
}

class Dog extends Animal {
    @Override
    void sound() { System.out.println("멍멍"); }  // 재정의
}
```

### 3.4 Static vs Instance

```java
class Counter {
    static int staticCount = 0;    // 클래스 변수 (모든 인스턴스 공유)
    int instanceCount = 0;          // 인스턴스 변수 (각 객체마다 독립)
    
    static void staticMethod() {
        // static 메서드는 static 멤버만 접근 가능
        staticCount++;
        // instanceCount++;  // ❌ 컴파일 에러
    }
    
    void instanceMethod() {
        // 인스턴스 메서드는 모두 접근 가능
        staticCount++;
        instanceCount++;
    }
}

Counter c1 = new Counter();
Counter c2 = new Counter();
c1.instanceCount = 5;
Counter.staticCount = 10;

System.out.println(c2.instanceCount);  // 0 (독립적)
System.out.println(c2.staticCount);    // 10 (공유됨)
```

### 3.5 final 키워드의 다양한 용도

```java
// 1. final 변수 - 상수
final int MAX_SIZE = 100;
// MAX_SIZE = 200;  // ❌ 컴파일 에러

// 2. final 메서드 - 오버라이딩 불가
class Parent {
    final void cannotOverride() { }
}

class Child extends Parent {
    // void cannotOverride() { }  // ❌ 컴파일 에러
}

// 3. final 클래스 - 상속 불가
final class Utility {
    // ...
}

// class MyUtil extends Utility { }  // ❌ 컴파일 에러

// 4. final 매개변수 - 재할당 불가
void process(final String param) {
    // param = "new value";  // ❌ 컴파일 에러
}

// 5. final 참조 - 참조는 변경 불가, 객체 내용은 변경 가능
final List<String> list = new ArrayList<>();
list.add("Hello");  // ✅ 가능 (내용 변경)
// list = new ArrayList<>();  // ❌ 불가 (참조 변경)
```

### 3.6 Checked vs Unchecked Exception

```java
// Checked Exception (컴파일 시점 체크, 반드시 처리)
void readFile() throws IOException {  // 명시 필수
    FileReader fr = new FileReader("file.txt");
}

void caller() {
    try {
        readFile();  // 반드시 try-catch 또는 throws 필요
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Unchecked Exception (런타임 시점, 처리 선택)
void divide(int a, int b) {  // throws 명시 불필요
    int result = a / b;  // ArithmeticException 가능
}

void caller2() {
    divide(10, 0);  // try-catch 없이도 컴파일 가능
}
```

| 구분 | Checked | Unchecked |
|------|---------|-----------|
| **부모 클래스** | `Exception` | `RuntimeException` |
| **처리 강제** | ✅ 필수 | ❌ 선택 |
| **예시** | IOException, SQLException | NullPointerException, IndexOutOfBoundsException |
| **용도** | 복구 가능한 외부 문제 | 프로그래밍 오류 |

### 3.7 this vs super

```java
class Parent {
    String name = "부모";
    
    void print() {
        System.out.println("부모 메서드");
    }
}

class Child extends Parent {
    String name = "자식";
    
    void print() {
        System.out.println("자식 메서드");
    }
    
    void display() {
        System.out.println(this.name);    // "자식" (현재 객체)
        System.out.println(super.name);   // "부모" (부모 객체)
        
        this.print();   // "자식 메서드" (현재 클래스)
        super.print();  // "부모 메서드" (부모 클래스)
    }
}
```

### 3.8 Primitive Type vs Wrapper Class

```java
// Primitive (기본형)
int a = 10;              // Stack에 직접 저장
int b = a;               // 값 복사
b = 20;
System.out.println(a);   // 10 (영향 없음)

// Wrapper (참조형)
Integer x = 10;          // Auto-boxing
Integer y = x;           // 참조 복사
y = 20;
System.out.println(x);   // 10 (Integer는 불변)

// 주의: null 가능 여부
int primitiveInt = null;     // ❌ 컴파일 에러
Integer wrapperInt = null;   // ✅ 가능

// 성능 차이
int[] primitiveArray = new int[1000000];      // 빠름
Integer[] wrapperArray = new Integer[1000000]; // 느림 (객체 생성)
```

---

## 4. 실전 팁

### 4.1 패키지 루트 문제 해결

**증상**: `java.lang.ClassNotFoundException` 또는 `package does not exist`

**해결 방법**:
1. 패키지 선언과 디렉토리 구조가 일치하는지 확인
2. 클래스패스에 올바른 루트 디렉토리가 포함되어 있는지 확인
3. IDE에서 소스 루트가 올바르게 설정되어 있는지 확인

```bash
# 디버깅 명령어
javac -verbose -cp src src/com/example/Main.java
java -verbose:class -cp bin com.example.Main
```

### 4.2 Record 사용 시점

**Record를 사용해야 할 때**:
- DTO (Data Transfer Object)
- 불변 데이터 모델
- 값 기반 비교가 필요한 데이터

**Class를 사용해야 할 때**:
- 복잡한 비즈니스 로직
- 상속 구조 필요
- 가변 객체

### 4.3 IDE 자동 완성 활용

**IntelliJ IDEA**:
- `psvm` → `public static void main(String[] args)`
- `sout` → `System.out.println()`
- `fori` → `for (int i = 0; i < ; i++)`

**Eclipse**:
- `main` + Ctrl+Space → main 메서드
- `syso` + Ctrl+Space → `System.out.println()`
