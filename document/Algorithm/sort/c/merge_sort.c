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
Merge Sort
- Stable: Yes
- In-place: No
- Time: O(n log n)
- Space: O(n)
*/
#include <stdlib.h>

static void merge(int *a, int l, int m, int r) {
    int n1 = m - l + 1;
    int n2 = r - m;
    int *L = (int*)malloc(n1 * sizeof(int));
    int *R = (int*)malloc(n2 * sizeof(int));
    for (int i = 0; i < n1; ++i) L[i] = a[l + i];
    for (int j = 0; j < n2; ++j) R[j] = a[m + 1 + j];
    int i = 0, j = 0, k = l;
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) a[k++] = L[i++];
        else a[k++] = R[j++];
    }
    while (i < n1) a[k++] = L[i++];
    while (j < n2) a[k++] = R[j++];
    free(L); free(R);
}

static void msort(int *a, int l, int r) {
    if (l >= r) return;
    int m = l + (r - l) / 2;
    msort(a, l, m);
    msort(a, m + 1, r);
    merge(a, l, m, r);
}

int main(void) {
    int a[] = {38,27,43,3,9,82,10};
    int n = sizeof(a)/sizeof(a[0]);
    msort(a, 0, n - 1);
    print_array(a, n);
    return 0;
}
