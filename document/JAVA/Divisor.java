/**
 * 연습 문제 4
 * 파일 이름 : Divisor.java
 */

import java.util.Scanner;

public class Divisor {
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a positive integer : ");
        int number = input.nextInt();

        System.out.println("The divisors of number " + number + " as follows.");

        for (int i = 1; i <= number; ++i) {
            // short 
            if (number % i == 0 & i % 2 == 0) {
                System.out.print("  " + i);
            }
        }
    }     
}
