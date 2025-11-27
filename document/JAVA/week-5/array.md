# [Java] 배열 (Array)과 참조 자료형

> **한 줄 요약**: 동일한 타입의 데이터를 연속된 메모리 공간에 저장하는 자료구조로, Java에서는 객체로 취급되어 Heap 영역에 저장되고 참조값(주소)을 통해 접근합니다.

---

## 1. 개념 (Concept)

### 1.1 정의
- **What**: 같은 타입의 여러 변수를 하나의 묶음으로 다루는 자료구조입니다.
- **Why**: 
    - **효율성**: 관련된 데이터를 한 번에 관리하고 반복문 등을 통해 효율적으로 처리할 수 있습니다.
    - **메모리 연속성**: 데이터가 메모리 상에 연속적으로 위치하여 접근 속도가 빠릅니다(Cache Locality).

### 1.2 핵심 원리 (Core Principles)
- **참조 자료형 (Reference Type)**: 배열 변수는 실제 데이터가 아닌, 데이터가 저장된 **Heap 메모리의 주소값**을 가집니다.
- **고정 크기**: 생성 시 크기가 정해지면 변경할 수 없습니다.

```mermaid
graph LR
    Stack[Stack Area]
    Heap[Heap Area]
    
    subgraph Stack
        arr[int[] arr]
    end
    
    subgraph Heap
        ArrayObj[Array Object<br/>Index 0: 10<br/>Index 1: 20<br/>Index 2: 30]
    end
    
    arr -->|Reference (Address)| ArrayObj
```

---

## 2. 구현 및 사용법 (Implementation)

### 2.1 기본 문법
- 선언, 생성, 초기화의 세 단계로 나뉩니다.

```java
// 1. 선언 및 생성 (기본값 0으로 초기화)
int[] numbers = new int[5]; 

// 2. 선언과 동시에 초기화
String[] names = {"Alice", "Bob", "Charlie"};

// 3. 값 접근 및 변경
numbers[0] = 10;
System.out.println(names[1]); // "Bob"
```

### 2.2 주요 예제: 최소값 구하기 (GetMin)
- 배열을 순회하며 값을 비교하는 전형적인 패턴입니다.

```java
public class GetMin {
    public static void main(String[] args) {
        int[] data = {5, 2, 9, 1, 7};
        
        // 첫 번째 요소를 초기 최소값으로 설정
        int min = data[0];
        
        // 배열 순회
        for (int i = 1; i < data.length; i++) {
            if (data[i] < min) {
                min = data[i]; // 최소값 갱신
            }
        }
        
        System.out.println("Minimum value: " + min);
    }
}
```

---

## 3. 심화 (Deep Dive)

### 3.1 메모리 구조와 참조 (Memory & Reference)
- **기본 자료형(Primitive Type)**: `int`, `double` 등은 변수 공간(Stack)에 **실제 값**이 저장됩니다.
- **참조 자료형(Reference Type)**: 배열, 클래스 등은 변수 공간(Stack)에 **주소값**이 저장되고, 실제 데이터는 Heap에 존재합니다.

```java
int[] a = {1, 2, 3};
int[] b = a; // 주소값 복사 (얕은 복사)

b[0] = 99;
System.out.println(a[0]); // 99 출력 (a와 b는 같은 객체를 가리킴)
```

### 3.2 얕은 복사 vs 깊은 복사 (Shallow vs Deep Copy)
- **얕은 복사 (Shallow Copy)**: 주소값만 복사합니다. 원본과 사본이 같은 객체를 공유하므로 한쪽을 변경하면 다른 쪽도 영향을 받습니다. (`int[] b = a;`)
- **깊은 복사 (Deep Copy)**: 새로운 객체를 생성하고 내부의 값들을 모두 복사합니다. 서로 독립적인 객체가 됩니다.

```java
// 깊은 복사 예시
int[] original = {1, 2, 3};
int[] deepCopy = new int[original.length];

for (int i = 0; i < original.length; i++) {
    deepCopy[i] = original[i];
}
// 또는 System.arraycopy()나 clone() 사용
// int[] deepCopy = original.clone();
```

### 3.3 다차원 배열
- Java의 2차원 배열은 "배열의 배열"입니다. 즉, 1차원 배열의 참조값을 저장하는 배열입니다.

```mermaid
graph LR
    matrix[int[][] matrix] --> rowRef[Row References]
    rowRef -->|Index 0| row0[Row 0 Array]
    rowRef -->|Index 1| row1[Row 1 Array]
```

---

## 4. 요약 및 체크리스트 (Summary)

- [ ] 배열은 같은 타입의 데이터를 연속적으로 저장하며, 크기가 고정된다.
- [ ] 배열 변수는 실제 값이 아닌 Heap 영역의 주소값을 저장하는 참조 변수이다.
- [ ] 배열을 다른 변수에 대입하면 주소값만 복사(얕은 복사)되어 객체를 공유하게 된다.
- [ ] 독립적인 배열을 만들려면 값을 일일이 복사하거나 `clone()` 등을 사용하여 깊은 복사를 해야 한다.
- [ ] 인덱스는 0부터 시작하며, 범위를 벗어나면 `ArrayIndexOutOfBoundsException`이 발생한다.

---
*Ref: Java Language Specification - Arrays*