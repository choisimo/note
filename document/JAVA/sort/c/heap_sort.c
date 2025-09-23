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
Heap Sort (max-heap)
- Stable: No
- In-place: Yes
- Time: O(n log n)
- Space: O(1)
*/
static void heapify(int *a, int n, int i) {
    int largest = i;
    int l = 2 * i + 1;
    int r = 2 * i + 2;
    if (l < n && a[l] > a[largest]) largest = l;
    if (r < n && a[r] > a[largest]) largest = r;
    if (largest != i) {
        int t = a[i]; a[i] = a[largest]; a[largest] = t;
        heapify(a, n, largest);
    }
}

static void heap_sort(int *a, int n) {
    for (int i = n/2 - 1; i >= 0; --i) heapify(a, n, i);
    for (int i = n - 1; i > 0; --i) {
        int t = a[0]; a[0] = a[i]; a[i] = t;
        heapify(a, i, 0);
    }
}

int main(void) {
    int a[] = {12,11,13,5,6,7};
    int n = sizeof(a)/sizeof(a[0]);
    heap_sort(a, n);
    print_array(a, n);
    return 0;
}
