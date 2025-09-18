"""
Insertion Sort
- Stable: Yes
- In-place: Yes
- Time: O(n^2) average/worst, O(n) best (nearly sorted)
- Space: O(1)
"""

def insertion_sort(arr):
    for i in range(1, len(arr)):
        key = arr[i]
        j = i - 1
        # Move elements of arr[0..i-1], that are greater than key,
        # to one position ahead of their current position
        while j >= 0 and arr[j] > key:
            arr[j + 1] = arr[j]
            j -= 1
        arr[j + 1] = key
    return arr

if __name__ == "__main__":
    data = [12, 11, 13, 5, 6]
    print("before:", data)
    print("after :", insertion_sort(data))
