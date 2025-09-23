"""
Merge Sort
- Stable: Yes
- In-place: No (uses extra memory)
- Time: O(n log n)
- Space: O(n)
"""

def merge_sort(arr):
    # Recursive divide-and-conquer
    if len(arr) <= 1:
        return arr
    mid = len(arr) // 2
    left = merge_sort(arr[:mid])
    right = merge_sort(arr[mid:])
    return _merge(left, right)


def _merge(left, right):
    result = []
    i = j = 0
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:  # <= keeps stability
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
    result.extend(left[i:])
    result.extend(right[j:])
    return result

if __name__ == "__main__":
    data = [38, 27, 43, 3, 9, 82, 10]
    print("before:", data)
    print("after :", merge_sort(data))
