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
Selection Sort
- Stable: No
- In-place: Yes
- Time: O(n^2)
- Space: O(1)
*/
void selection_sort(int *a, int n) {
    for (int i = 0; i < n - 1; ++i) {
        int min_idx = i;
        for (int j = i + 1; j < n; ++j) {
            if (a[j] < a[min_idx]) min_idx = j;
        }
        if (min_idx != i) {
            int t = a[i]; a[i] = a[min_idx]; a[min_idx] = t;
        }
    }
}

int main(void) {
    int a[] = {64,25,12,22,11};
    int n = sizeof(a)/sizeof(a[0]);
    selection_sort(a, n);
    print_array(a, n);
    return 0;
}
