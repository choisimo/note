/*
Merge Sort
- Stable: Yes
- In-place: No
- Time: O(n log n)
- Space: O(n)
*/
public class MergeSort {
    static void printArr(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }
    private static void merge(int[] a, int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; i++) L[i] = a[l + i];
        for (int j = 0; j < n2; j++) R[j] = a[m + 1 + j];
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) a[k++] = L[i++];
            else a[k++] = R[j++];
        }
        while (i < n1) a[k++] = L[i++];
        while (j < n2) a[k++] = R[j++];
    }
    private static void sortRec(int[] a, int l, int r) {
        if (l >= r) return;
        int m = l + (r - l) / 2;
        sortRec(a, l, m);
        sortRec(a, m + 1, r);
        merge(a, l, m, r);
    }
    public static void sort(int[] a) { sortRec(a, 0, a.length - 1); }
    public static void main(String[] args) {
        int[] a = {38,27,43,3,9,82,10};
        sort(a);
        printArr(a);
    }
}
