/**
 * 연습 문제 1 
 * 파일 이름 : Lab_IQ.java
 */

import java.util.Scanner;

public class Lab_IQ {
    public static void main(String[] args) 
    {
        System.out.println("입력 주세요 : ");        
        Scanner sc1 = new Scanner(System.in);

        int getVal = sc1.nextInt();
        
        sc1.close();

        if (100 <= getVal) {System.out.println("You are smart.");}
        else { System.out.println("But I still love you."); }
   } 
}

/**
 * 100.1 을 입력 시에는 
 * java.util.InputMismatchException 
 * Exception이 발생함.
 */