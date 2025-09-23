import java.util.Scanner;

public class quickSort {
    private static int swapCount = 0;
    private static int partition(int[] A, int p,  int r) {
        int x = A[r]; // last : right, init-pivot
        int i = p - 1; //  start index of partition 
        
        for (int j = p; j < r; j++) {
            // pivot 값보다 작을 때 
            if (A[j] <= x ) {
                i++;
                swap(A, i, j);
                swapCount++;
            }
        }
        swap(A, i++, r);
        swapCount++;
        // return the partition index 
        return ++i;
    }

    private static void sort(int[] A, int start, int end) {
        int partition = partition(A, start, end);
    
    }

    private static void swap(int[] A, int x, int y) {
        int temp = A[x];
        A[x] = A[y];
        A[y] = temp;
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();

            if (n < 1 | n > 10000) {
                sc.close();
                return;
            }

            int[] A = new int[n];
            int t_cnt = 0;
            while (t_cnt < n) {
                A[t_cnt++] = sc.nextInt(); 
            }
            sc.close();

            int p = 0, r = A.length - 1;

            int q = partition(A, p, r);
            sort(A, p, q);
            sort(A, q + 1, r);

        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}