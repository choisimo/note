import java.util.Scanner;

public class quickSort {
    private static int swapCount = 0;

    private static int partition(int[] A, int p, int r) {
        int pivot = A[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (A[j] <= pivot) {
                i++;
                // i==j 여도 호출 -> 1회 집계 추가
                swap(A, i, j); 
            }
        }
        swap(A, i + 1, r);
        return i + 1;
    }

    private static void sort(int[] A, int start, int end) {
        if (start < end) {
            int q = partition(A, start, end);
            sort(A, start, q - 1);
            sort(A, q + 1, end);
        }
    }

    private static void swap(int[] A, int x, int y) {
        int temp = A[x];
        A[x] = A[y];
        A[y] = temp;
        swapCount++; // 모든 swap 호출을 1회로 카운트
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = sc.nextInt();
        }
        sc.close();

        sort(A, 0, n - 1);
        System.out.println(swapCount);
    }
}
