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
Heap Sort (max-heap)
- Stable: No
- In-place: Yes
- Time: O(n log n)
- Space: O(1)
*/
static void heapify(vector<int>& a, int n, int i) {
    int largest = i;
    int l = 2 * i + 1;
    int r = 2 * i + 2;
    if (l < n && a[l] > a[largest]) largest = l;
    if (r < n && a[r] > a[largest]) largest = r;
    if (largest != i) {
        swap(a[i], a[largest]);
        heapify(a, n, largest);
    }
}

static void heap_sort(vector<int>& a) {
    int n = (int)a.size();
    for (int i = n/2 - 1; i >= 0; --i) heapify(a, n, i);
    for (int i = n - 1; i > 0; --i) {
        swap(a[0], a[i]);
        heapify(a, i, 0);
    }
}

int main() {
    vector<int> a{12,11,13,5,6,7};
    heap_sort(a);
    print_vec(a);
}
