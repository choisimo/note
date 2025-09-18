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
Bubble Sort
- Stable: Yes
- In-place: Yes
- Time: O(n^2) average/worst, O(n) best
- Space: O(1)
*/
void bubble_sort(int *a, int n) {
    for (int i = 0; i < n - 1; ++i) {
        bool swapped = false;
        for (int j = 0; j < n - 1 - i; ++j) {
            if (a[j] > a[j + 1]) {
                int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t;
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}

int main(void) {
    int a[] = {5,1,4,2,8};
    int n = sizeof(a)/sizeof(a[0]);
    bubble_sort(a, n);
    print_array(a, n);
    return 0;
}
