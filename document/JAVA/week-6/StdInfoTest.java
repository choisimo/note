import java.util.Scanner;

public class StdInfoTest { 
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        StdInfo std1  = new StdInfo();
        System.out.println("Std1 name : ");
        std1.name = input.next();
        System.out.println("Std1 age : ");
        std1.age = input.nextInt();
        System.out.println("Std1 name : ");
        std1.major = input.next();
        System.out.println();
    }
};