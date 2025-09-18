/*
Quick Sort (Lomuto)
- Stable: No
- In-place: Yes
- Time: O(n log n) avg, O(n^2) worst
- Space: O(log n) recursion avg
*/
public class QuickSort {
    static void printArr(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }
    private static int partition(int[] a, int low, int high) {
        int pivot = a[high];
        int i = low;
        for (int j = low; j < high; j++) {
            if (a[j] <= pivot) {
                int t = a[i]; a[i] = a[j]; a[j] = t;
                i++;
            }
        }
        int t = a[i]; a[i] = a[high]; a[high] = t;
        return i;
    }
    private static void sortRec(int[] a, int low, int high) {
        if (low < high) {
            int p = partition(a, low, high);
            sortRec(a, low, p - 1);
            sortRec(a, p + 1, high);
        }
    }
    public static void sort(int[] a) { sortRec(a, 0, a.length - 1); }
    public static void main(String[] args) {
        int[] a = {10,7,8,9,1,5};
        sort(a);
        printArr(a);
    }
}
