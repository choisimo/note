# Java Data Types & Memory Model (자료형과 메모리 구조)

> **한 줄 요약**: Java의 기본 자료형과 참조 자료형의 차이를 이해하고, JVM 메모리(Stack/Heap)에서의 동작 원리를 파악합니다.

---

## 1. 개념 (Concept)

### 1.1 자료형의 분류
Java의 데이터 타입은 크게 **기본 자료형(Primitive Type)**과 **참조 자료형(Reference Type)**으로 나뉩니다.

| 구분 | 설명 | 저장 위치 (값) | 예시 |
| :--- | :--- | :--- | :--- |
| **Primitive Type** | 실제 데이터 값을 저장 | **Stack** | `int`, `double`, `boolean`, `char` 등 (8종) |
| **Reference Type** | 데이터가 저장된 주소(참조값)를 저장 | **Heap** (실제 객체), **Stack** (참조 주소) | `String`, `Array`, `Class`, `Interface` |

### 1.2 메모리 구조 (Stack vs Heap)
- **Stack 영역**: 메서드 호출 시 생성되는 프레임(Frame) 내부에 변수들이 저장됩니다. 메서드 종료 시 자동 소멸됩니다.
- **Heap 영역**: `new` 키워드로 생성된 객체와 배열이 저장됩니다. Garbage Collector(GC)에 의해 관리됩니다.

```mermaid
graph LR
    subgraph Stack [Stack Memory]
        A[int a = 10]
        B[String s (ref)]
    end
    subgraph Heap [Heap Memory]
        C[Object "Hello"]
    end
    B -->|Points to| C
```

---

## 2. 구현 및 상세 (Implementation)

### 2.1 기본 자료형 (Primitive Types)
Java는 8가지 기본 자료형을 제공합니다.

- **정수형**: `byte`(1B), `short`(2B), `int`(4B), `long`(8B)
- **실수형**: `float`(4B), `double`(8B)
- **문자형**: `char`(2B) - Unicode 사용 (C/C++의 1byte와 다름)
- **논리형**: `boolean` (JVM 구현에 따라 다르나 보통 1byte 취급, 실제 사용은 1bit 정보)

```java
int age = 25;          // Stack에 25 저장
double pi = 3.14;      // Stack에 3.14 저장
char c = 'A';          // Stack에 'A' (Unicode 65) 저장
boolean isActive = true;
```

### 2.2 참조 자료형 (Reference Types)
기본 자료형을 제외한 모든 타입입니다.

```java
String name = "Java";  // Stack의 name 변수는 Heap의 "Java" 객체 주소를 가짐
int[] arr = {1, 2, 3}; // Stack의 arr 변수는 Heap의 배열 객체 주소를 가짐
```

---

## 3. 심화 (Deep Dive)

### 3.1 JVM에서의 동작 원리
1. **변수 선언 및 할당**:
   - `int a = 10;` 실행 시, 현재 스레드의 Stack Frame 내 Local Variable Array에 `10`이 저장됩니다.
   - `String s = "Hi";` 실행 시, Heap에 String 객체(또는 String Constant Pool)가 생성되고, Stack의 변수 `s`에는 그 메모리 주소값(예: `0x1234`)이 저장됩니다.

2. **Call by Value**:
   - Java는 항상 **Call by Value**로 동작합니다.
   - 기본 자료형을 메서드로 넘기면 **값 자체**가 복사됩니다.
   - 참조 자료형을 메서드로 넘기면 **주소값**이 복사됩니다. (따라서 메서드 내에서 객체의 필드를 바꾸면 원본도 바뀌지만, 변수 자체가 가리키는 객체를 다른 객체로 바꿀 수는 없습니다.)

### 3.2 문자열(char)과 유니코드
- C/C++의 `char`는 보통 ASCII(1byte)이지만, Java의 `char`는 **UTF-16 BE(2byte)**를 사용합니다.
- 따라서 한글 '가'와 같은 문자도 `char` 변수 하나에 저장 가능합니다.

### 3.3 Boolean의 크기
- Java 명세서에는 `boolean`의 크기가 명시적으로 정의되어 있지 않습니다.
- 배열(`boolean[]`)의 경우 `byte` 배열처럼 1byte씩 사용되는 경우가 많고, 단일 변수는 `int`로 취급되기도 하는 등 JVM 구현체(Oracle, OpenJDK 등)에 따라 최적화 방식이 다릅니다.

---

## 4. 요약 및 체크리스트 (Summary)
- [ ] 기본 자료형은 Stack에 값 저장, 참조 자료형은 Stack에 주소/Heap에 값 저장.
- [ ] Java의 `char`는 2byte 유니코드이다.
- [ ] Java는 항상 Call by Value로 동작한다 (참조 타입은 주소값이 복사됨).
- [ ] Stack의 데이터는 스코프 종료 시 소멸, Heap의 데이터는 GC가 관리.

---
*Ref: Java Language Specification, JVM Specification*