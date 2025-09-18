import java.util.Scanner;
public class Gugudan {
    public static void main (String[] args) 
    {
        int number;
        int i = 1;
        int curLvl =2;

        System.out.println("Please enter a number: ");

        Scanner input = new Scanner(System.in);
        number = input.nextInt();
        input.close();

        while (curLvl <= 8)
        {
            while (i <= 9) {
                System.out.println(number + "*" + i + "=" + number * i);
                i++;
            }
            curLvl++;
        }
    }
}