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
Selection Sort
- Stable: No
- In-place: Yes
- Time: O(n^2)
- Space: O(1)
*/
void selection_sort(vector<int>& a) {
    int n = (int)a.size();
    for (int i = 0; i < n - 1; ++i) {
        int min_idx = i;
        for (int j = i + 1; j < n; ++j) if (a[j] < a[min_idx]) min_idx = j;
        if (min_idx != i) swap(a[i], a[min_idx]);
    }
}

int main() {
    vector<int> a{64,25,12,22,11};
    selection_sort(a);
    print_vec(a);
}
