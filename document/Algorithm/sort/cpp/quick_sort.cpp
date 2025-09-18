#include <bits/stdc++.h>
using namespace std;

static void print_vec(const vector<int>& v) {
    for (size_t i = 0; i < v.size(); ++i) {
        if (i) cout << ' ';
        cout << v[i];
    }
    cout << "
";
}

/*
Quick Sort (Lomuto)
- Stable: No
- In-place: Yes
- Time: O(n log n) avg, O(n^2) worst
- Space: O(log n) recursion avg
*/
static int partition(vector<int>& a, int low, int high) {
    int pivot = a[high];
    int i = low;
    for (int j = low; j < high; ++j) {
        if (a[j] <= pivot) swap(a[i++], a[j]);
    }
    swap(a[i], a[high]);
    return i;
}

static void qsort_rec(vector<int>& a, int low, int high) {
    if (low < high) {
        int p = partition(a, low, high);
        qsort_rec(a, low, p - 1);
        qsort_rec(a, p + 1, high);
    }
}

int main() {
    vector<int> a{10,7,8,9,1,5};
    qsort_rec(a, 0, (int)a.size() - 1);
    print_vec(a);
}
