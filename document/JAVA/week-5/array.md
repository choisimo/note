### GetMin.java 
-   최소값을 구하는 예제
- 배열의 첫 번째 element 를 최소값(minimum) 으로 설정 
- 배열 순회, 최소값 갱신

### 자료형 변수 
- 기본 자료형(primitive type) : int, double, char, boolean
- 참조 자료형(reference type) : String, 배열(array), 클래스(class)
    - 참조 자료형은 변수에 값이 아닌 주소(address) 저장 (배열에는 변수의 주소가 저장됨)
    - 배열은 참조 자료형이므로 new 연산자로 생성
    
    ```java
    int[] a = {3, 4, 5};
    int[] b = a; // a 와 b 는 같은 배열을 참조                            a 와 b 는 같은 주소를 가짐
    b[0] = 7;    // a[0] 도 7 로 변경됨
    ```
    
    