import java.util.Scanner;

public class GetMin {

    private static int minimum = -111;

    private static void searchMinimum(int[] Array) {
        if ((Array.length < 1) | Array.length - ) {
            return;
        }
        
        int p = Array[0];
        int r = Array[Array.length - 1];
        int q =  (p + r) / 2;
        
        // block outbounding 
        while (p < r)
        {
            
        }
    }

    public static void main(String[] args) 
    {
        int s[] = { 12, 3, 19, 6, 18, 8, 12, 4, 1, 19};
        int minimum;

        // first-index value of array S 
        minimum = s[0];

        searchMinimum(s);
        System.out.println("Minimum is " + minimum);
    }     
}
