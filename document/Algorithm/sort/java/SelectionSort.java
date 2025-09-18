/*
Selection Sort
- Stable: No
- In-place: Yes
- Time: O(n^2)
- Space: O(1)
*/
public class SelectionSort {
    static void printArr(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }
    public static void sort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) if (a[j] < a[minIdx]) minIdx = j;
            if (minIdx != i) {
                int t = a[i]; a[i] = a[minIdx]; a[minIdx] = t;
            }
        }
    }
    public static void main(String[] args) {
        int[] a = {64,25,12,22,11};
        sort(a);
        printArr(a);
    }
}
