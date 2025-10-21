import java.util.Scanner;

public class countingSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();          
        
        if (!(n < 1 || n > 1000000)) {
            
        int[] a = new int[n];
        int min = Integer.MAX_VALUE; 
        int max = Integer.MIN_VALUE; 

        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
            if (a[i] < min) min = a[i]; 
            if (a[i] > max) max = a[i]; 
        }

        // // 개수를 세기 위한 배열 (0 ~ max)
        int k = max - min + 1;             
        int[] count = new int[k];          

        // 각 값이 몇 번 나오는지 카운트
        for (int i = 0; i < n; i++) {
            count[a[i] - min]++; 
        }

        // 누적합 만들기 (작은 값이 몇 개 있는지 알 수 있음)
        for (int i = 1; i < count.length; i++) { 
            count[i] += count[i -1];
        }

        // 누적 배열  출력
        for (int i = 0; i < count.length; i++) { 
            if (i > 0) System.out.print(" ");
            System.out.print(count[i]);
        }
            sc.close();
        } else {
            System.out.println("원소 개수는 1 이상 1,000,000 이하입니다.");
        }
    }
}
