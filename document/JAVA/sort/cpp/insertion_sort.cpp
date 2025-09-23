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
Insertion Sort
- Stable: Yes
- In-place: Yes
- Time: O(n^2) average/worst, O(n) best
- Space: O(1)
*/
void insertion_sort(vector<int>& a) {
    for (size_t i = 1; i < a.size(); ++i) {
        int key = a[i];
        int j = (int)i - 1;
        while (j >= 0 && a[j] > key) {
            a[j + 1] = a[j];
            --j;
        }
        a[j + 1] = key;
    }
}

int main() {
    vector<int> a{12,11,13,5,6};
    insertion_sort(a);
    print_vec(a);
}
