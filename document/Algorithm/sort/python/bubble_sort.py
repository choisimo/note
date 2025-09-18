"""
Bubble Sort
- Stable: Yes
- In-place: Yes
- Time: O(n^2) average/worst, O(n) best (already sorted)
- Space: O(1)
"""

def bubble_sort(arr):
    n = len(arr)
    # Traverse the list n-1 times
    for i in range(n - 1):
        swapped = False  # Track if any swap happened in this pass
        # Last i elements are already in place
        for j in range(0, n - 1 - i):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
                swapped = True
        # Early exit if no swaps => already sorted
        if not swapped:
            break
    return arr

if __name__ == "__main__":
    data = [5, 1, 4, 2, 8]
    print("before:", data)
    print("after :", bubble_sort(data))
