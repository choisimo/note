"""
Heap Sort (max-heap)
- Stable: No
- In-place: Yes
- Time: O(n log n)
- Space: O(1)
"""

def heap_sort(arr):
    n = len(arr)

    def heapify(a, n, i):
        # Assume i is largest; try to expand to children
        largest = i
        l = 2 * i + 1
        r = 2 * i + 2

        if l < n and a[l] > a[largest]:
            largest = l
        if r < n and a[r] > a[largest]:
            largest = r
        if largest != i:
            a[i], a[largest] = a[largest], a[i]
            heapify(a, n, largest)

    # Build max heap
    for i in range(n // 2 - 1, -1, -1):
        heapify(arr, n, i)

    # Extract elements one by one
    for i in range(n - 1, 0, -1):
        arr[0], arr[i] = arr[i], arr[0]
        heapify(arr, i, 0)
    return arr

if __name__ == "__main__":
    data = [12, 11, 13, 5, 6, 7]
    print("before:", data)
    print("after :", heap_sort(data))
