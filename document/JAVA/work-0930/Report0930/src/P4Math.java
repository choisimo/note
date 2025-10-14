public class P4Math {
    int add (int x, int y) {
        return x + y;
    }    

    int multiply (int x, int y) {
        return x * y;
    }

    int divisor (int x, int y) {
        return x / y;
    }

    public static void main(String[] args) {
        P4Math math = new P4Math();
        int a1 = math.add(1, 6);
        int a2 = math.multiply(10, 5);
        int a3 =  math.divisor(5, 2);

        System.out.println("add : " + a1);
        System.out.println("multiply : " + a2);
        System.out.println("divisor : " + a3);
    }
}
