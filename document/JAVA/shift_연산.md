## shift 연산자
- 비트의 위치를 좌우로 이동
- 산술 쉬프트
    - <<, >> :
     부호 비트는 유지하면서 나머지 비트를 왼/오 으로 이동
- 논리 쉬프트
    - '>>>' : 
    부호 비트를 포함하여 

### 예제 코드 
```java
public class example {
    public static void main (String[] args) {
        System.out.println("" + Integer.toBinaryString(0b00000101 << 2));
        ... (나머지 대충 코드들)
        System.out.println("" + Integer.toBinaryString(0b00000101 <<< 2));
        ... (나머지 대충 코드들)
    }
}
```
## 비교 연산자
- 두 변수의 크기를 비교함
    -   기호: <, >, <=,  =>
- 두 변수의 등가를 비교함
    - 기호: ==, !=
    - 등호 1개만 쓰는 경우에는 (=) 비교가 아닌 대입인 것에 **주의**할 것
> tip: 2 == a 와 a == 2 중 **2==a** 를 사용하면 좋음
    - 대입 연산자 오타 방지 가능
, 대입 연산자 오타 시에 compile error 발생하기 때문에 디버깅에 좋다.
```java
if (2 == a) {       // if (2 = a) 으로 오타 시 컴파일 에러 발생
    ...(대충 코드)
}
```

```java
if (a == 2) {       // if (a = 2) 으로 오타 시 컴파일 에러 발생 안 함
    ...(대충 코드)
}
```
### 주의점
-  등가 비교시 비교 대상은 **스택 메모리 값**
-  기본 자료형 데이터 값은 스택 메모리에 저장됨
-  기본 자료형 변수의 비교 대상은 실제 데이터 값이 된다.
-  참조 자료형의 데이터값은 힙 메모리 영역
-  참조 자료형 변수의 비교 대상은 실제 데이터가 위치한 주소값이 됨
-  문자 리터럴은 Heap 영역의 constant pool 에 별도 저장
> 문제가 되는 것?
> - 클래스 객체를 사용하는 경우 

```java
obj a = 3; 
obj b = 3;
```

```java
String a = "hello"; // heap 공간에 저장되어 있음 (hello)
// 주소 값 3333 이라면 -> a 는 stack 에 저장된 해당 heap 주소 값
String b = "hello";
if (a == b) {
    System.out.println("같음");
} 
// [추론] 이 경우 같지않으므로 출력 안 됨
// -> JAVA 컴파일러는 Heap 공간에 문자 리터럴을 위한 새로운 풀을 생성함 (상수 풀, constant pool)
// constant pool 에 "hello" 문자열 저장함 -> a, b 변수는 해당 상수 풀을 공유하므로 "같음" 이 출력됨 (같은 주소값을 가짐)
```
```bash
output: 같음 
```

### 예제 코드 (자료형의 주소값 차이)
```java
public class CompareOperation {
    public static void main (String[] args) {
        int num1 = 10, num2 = 10;
        int num3 = 10, num4 = 20;

        // 상수 리터럴 값이므로 같은 heap의 constant string pool 의 주소값 참조
        String str1 = "JBNU", str2 = "JBNU";

        // new -> 명시적으로 heap 공간 각각 지정함 - 서로 주소값이 다르다. 
        // (즉, constant pool 에 저장하지 않는다.)
        // 이게 **정석적인** 방법임. 
        String str3 = new String("JBNU"), str4 = new String("JBNU");

        System.out.println(num1 == num2); // true
        System.out.println(num3 == num4); // false
        
        System.out.println(str1 == str2); // true
        System.out.println(str3 == str4); // false

        System.out.println(str1 == str3); // false
        System.out.println(str2 == str4); // false


        // 꼭 쌍따옴표를 사용하자.
        
        /**
         * CompareOperation.java:11: error: unclosed character literal
        String tests1 = 'JBNU';
                        ^
        CompareOperation.java:11: error: unclosed character literal
        String tests1 = 'JBNU';
                             ^
        CompareOperation.java:11: error: not a statement
        String tests1 = 'JBNU';
                          ^
         */
        
        String tests1 = 'JBNU';
    }
}
```

## 논리 연산자
- AND(&&), OR(||), XOR(^), NOT (!)
> ####  bit 연산자는 잘 안 씀  - 논리 연산자를 주로 사용함
## 비트 연산자 
- &, | 등 하나만
### 쇼트 서킷 (short circuit)
- 연산 결과가 확정될 때 나머지 연산 과정을 생략하는 것
- 비트 연산자 연산 수행 시 **쇼트 서킷 적용 안** 함
- 논리 연산자 연산 수행 시 **쇼트 서킷 적용**함

> 비트 연산자는 쇼트 서킷 없으므로 상황에 맞게 잘 써보면 되겠다.

```java
public class ShortCircuit {
    public static void main(String[] args){
        int a = 1;

        // short circuit 실행됨
        System.out.println(true || (a++ < 2)); // true
        System.out.println(a);                              // 1 

        // short circuit 실행 안 됨
        System.out.println(true | (a++ < 2));  // true
        System.out.println(a);                              // 2
    }   
}
```

## 대입 연산자
- 다른 연산자와 결합하여 축약된 형태로 사용 가능함
## 삼항 연산자
- 자바 연산자 중에서 유일하게 3개의 피연산자가 존재함
- (참 또는 거짓) ? 참일 때의 결과 : 거짓일 때의 결과
```java
// 유틸 꼭 넣으셈 
import java.util.Scanner;

public class evenodd {
    public static void main(String[] args){ 
        int number;

        // Scanner 라는 '일종의' 데이터 타입? 
        Scanner input = new Scanner(System.in);

        System.out.println("숫자 기입 : ");
        // Memory-buffer 에 입력 값 스트림 저장
        number = input.nextInt();

        if (number % 2 == 0) {
            System.out.println("the value is 짝수임");
        } else
           System.out.println("the value is 홀수임");
    }
}
```

## JAVA 반복문
- 레이블 사용법 
```java
public class LabelExample {
    public static void main(String[] args) {       
        int i = 0;

        // outer 레이블 지정 
        outer: while (i < 5) {
            int j = 0;
            inner: while (j < 5) {
                if (i + j == 6) {
                    break outer; // outer 레이블이 붙은 while 문을 종료
                }
                System.out.println("i: " + i + ", j: " + j);
                j++;
            }
            i++;
        }
        System.out.println("End of program");
    }
}
```
> 레이블 outer 역할 - goto 와 비슷함? 
