
public class Test {
    static int fieldA;
    static int fieldB;
    static int sFieldA;

    public static void main(String[] args) {
        fieldA = 10; // 에러!
        fieldB = 20; // 에러!
        sFieldA = 30; // OK
        add(10, 20); // 에러!
        mul(30, 40); // OK
    }
    static int add(int x, int y) {
        return x + y;   
    }
    static int mul(int x, int y) {
        return x * y;
    }
}