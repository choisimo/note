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
Merge Sort
- Stable: Yes
- In-place: No
- Time: O(n log n)
- Space: O(n)
*/
static vector<int> merge_sort(vector<int> a) {
    if (a.size() <= 1) return a;
    size_t m = a.size() / 2;
    vector<int> left(a.begin(), a.begin() + m);
    vector<int> right(a.begin() + m, a.end());
    left = merge_sort(left);
    right = merge_sort(right);
    vector<int> res; res.reserve(a.size());
    size_t i = 0, j = 0;
    while (i < left.size() && j < right.size()) {
        if (left[i] <= right[j]) res.push_back(left[i++]);
        else res.push_back(right[j++]);
    }
    while (i < left.size()) res.push_back(left[i++]);
    while (j < right.size()) res.push_back(right[j++]);
    return res;
}

int main() {
    vector<int> a{38,27,43,3,9,82,10};
    a = merge_sort(a);
    print_vec(a);
}
