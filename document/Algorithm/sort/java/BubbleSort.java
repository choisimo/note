/*
Bubble Sort
- Stable: Yes
- In-place: Yes
- Time: O(n^2) average/worst, O(n) best
- Space: O(1)
*/
public class BubbleSort {
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
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
    public static void main(String[] args) {
        int[] a = {5,1,4,2,8};
        sort(a);
        printArr(a);
    }
}
