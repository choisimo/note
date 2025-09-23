"""
Quick Sort (Lomuto partition)
- Stable: No
- In-place: Yes
- Time: O(n log n) average, O(n^2) worst (e.g., sorted input with poor pivot)
- Space: O(log n) recursion average
"""

def quick_sort(arr):
    def partition(a, low, high):
        pivot = a[high]
        i = low
        for j in range(low, high):
            if a[j] <= pivot:
                a[i], a[j] = a[j], a[i]
                i += 1
        a[i], a[high] = a[high], a[i]
        return i

    def qs(a, low, high):
        if low < high:
            p = partition(a, low, high)
            qs(a, low, p - 1)
            qs(a, p + 1, high)

    qs(arr, 0, len(arr) - 1)
    return arr

if __name__ == "__main__":
    data = [10, 7, 8, 9, 1, 5]
    print("before:", data)
    print("after :", quick_sort(data))
