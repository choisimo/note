"""
Selection Sort
- Stable: No (unless modified)
- In-place: Yes
- Time: O(n^2)
- Space: O(1)
"""

def selection_sort(arr):
    n = len(arr)
    for i in range(n - 1):
        min_idx = i
        for j in range(i + 1, n):
            if arr[j] < arr[min_idx]:
                min_idx = j
        if min_idx != i:
            arr[i], arr[min_idx] = arr[min_idx], arr[i]
    return arr

if __name__ == "__main__":
    data = [64, 25, 12, 22, 11]
    print("before:", data)
    print("after :", selection_sort(data))
