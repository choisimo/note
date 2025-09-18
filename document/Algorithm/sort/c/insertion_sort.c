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
Insertion Sort
- Stable: Yes
- In-place: Yes
- Time: O(n^2) average/worst, O(n) best
- Space: O(1)
*/
void insertion_sort(int *a, int n) {
    for (int i = 1; i < n; ++i) {
        int key = a[i];
        int j = i - 1;
        while (j >= 0 && a[j] > key) {
            a[j + 1] = a[j];
            --j;
        }
        a[j + 1] = key;
    }
}

int main(void) {
    int a[] = {12,11,13,5,6};
    int n = sizeof(a)/sizeof(a[0]);
    insertion_sort(a, n);
    print_array(a, n);
    return 0;
}
