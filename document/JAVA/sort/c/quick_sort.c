#include <stdio.h>
#include <stdbool.h>

// Utility: print array
static void print_array(int *a, int n) {
    for (int i = 0; i < n; ++i) {
        if (i) printf(" ");
        printf("%d", a[i]);
    }
    printf("
");
}

/*
Quick Sort (Lomuto)
- Stable: No
- In-place: Yes
- Time: O(n log n) avg, O(n^2) worst
- Space: O(log n) recursion avg
*/
static int partition(int *a, int low, int high) {
    int pivot = a[high];
    int i = low;
    for (int j = low; j < high; ++j) {
        if (a[j] <= pivot) {
            int t = a[i]; a[i] = a[j]; a[j] = t;
            ++i;
        }
    }
    int t = a[i]; a[i] = a[high]; a[high] = t;
    return i;
}

static void qsort_rec(int *a, int low, int high) {
    if (low < high) {
        int p = partition(a, low, high);
        qsort_rec(a, low, p - 1);
        qsort_rec(a, p + 1, high);
    }
}

int main(void) {
    int a[] = {10,7,8,9,1,5};
    int n = sizeof(a)/sizeof(a[0]);
    qsort_rec(a, 0, n - 1);
    print_array(a, n);
    return 0;
}
