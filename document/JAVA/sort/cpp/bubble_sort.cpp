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
Bubble Sort
- Stable: Yes
- In-place: Yes
- Time: O(n^2) average/worst, O(n) best
- Space: O(1)
*/
void bubble_sort(vector<int>& a) {
    int n = (int)a.size();
    for (int i = 0; i < n - 1; ++i) {
        bool swapped = false;
        for (int j = 0; j < n - 1 - i; ++j) {
            if (a[j] > a[j + 1]) {
                swap(a[j], a[j + 1]);
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}

int main() {
    vector<int> a{5,1,4,2,8};
    bubble_sort(a);
    print_vec(a);
}
