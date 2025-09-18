import java.util.Scanner;
public class Gugudan {
    public static void main (String[] args) 
    {
        int number;
        int curLvl =2;

        System.out.println("Please enter a number: ");

        Scanner input = new Scanner(System.in);
        number = input.nextInt();
        input.close();

        while (curLvl <= number)
        {
            int i = 1;
            while (i <= 9) {
                System.out.println(curLvl + "*" + i + "=" + curLvl * i);
                i++;
            }
            curLvl++;
        }
    }
}