## 힙 정렬 (Max Heap)
> 힙의 특성 2가지
- **완전 이진 트리** 구조
- 모든 **부모 노드는 자식 노드들 값보다 크거나 같다**

### 완전 이진 트리 
- 이진 트리 - 자식 노드의 개수가 최대 2개인 트리의 특별한 형태
- **왼 -> 오** 차례대로 채워지는 트리 (why? 연속된 배열 공간에 할당 되기에 - 배열) 
    - 루트 노드의 index : 0 (모든 노드들 중에서 가장 큰 값을 가짐)
    - 부모 노드  index : i 이면
    - 자식 노드 index : 2 * i + 1, 2 * i + 2
- 마지막 레벨을 제외하면 모든 레벨이 꽉 차 있음

### Heapify 
- 마지막 비리프 노드 (자식있는 노드들) 에서 시작 
- 인덱스로 보면 (n - 2) / 2 (배열 크기가 n 일 때)
> 해당 노드에 대해서 Heapify 수행함
- 부모와 자식 중 큰 값이 부모 노드의 값이 되어야 한다.
- 교환한 후, 교환된 자식 노드 위치에서 다시 Heapify 
    - 즉, 루트 노드까지 각각 Heapify 반복적으로 수행한다. 
    - Heapify 수행 후 재귀적인 Heap 구조 무너지기 때문에 다시 하위 노드들 집합 다시 확인해야함
    - 동일 레벨 노드들의 재귀적인 Heap 구조 맞추면서 root node 까지 Heapify 수행

    ```bash
    buildHeap(Arr[]):
        for i <- (n - 2) / 2 downto 0
            percolateDown (Arr, i)
    ```

    ```bash
    percolateDown(Arr[], k):
        child <- 2k + 1                 # 왼쪽 자식 노드
        right <- 2k + 2                 # 오른쪽 자식 노드
        if (child <= n - 1)             # 배열 index 넘지 않도록
            if (right <= n - 1 && Arr[child] < Arr[right]):
                child <- right
            if (Arr[k] < Arr[child]):
                Arr[k] <-> Arr[child]
                percolateDown(Arr, child) 
    ```

### Heap 정렬 과정
1. **Root 노드**를 가장 끝에 있는 노드랑 교환 
    - (bubble, selection 과 같은 메커니즘) 
    - 배열 마지막 index 에 root 노드 위치하도록 

    ```bash
    deleteMax(Arr[]):
        max <- Arr[0]               # 가장 큰 값은 정렬 끝남
        Arr[0] <- Arr[n - 1]       # n - 1 개의 노드만 비교하면 됨

        n --

        percolateDown(Arr, 0)
        return max
    ```

    ```bash
    heapSort(Arr[]):
        buildHeap()
        # log (n) to log (1) = O (log (n!)) 의 근사값 := n log (n) - n
        for i <- n - 1 down to  1
            Arr[i] <- deleteMax(Arr)
    ```
    > 근사값 구하기 - strling 근사값 구하기 공식 적용

    2. 시간 복잡도
    > worst / best : Nlog(N)

    - 각 노드 별로 heapify 비용은 O(h) -> 2h 이지만 상수값 제외
    -   각 높이 (h) 에서 수행되는 heapify 의 비용은 최대 O(h) 
        - 단순한 멱수열을 적용 (생략함) 
        - h 레벨의 노드 개수는 최대 (n / 2 ^ (h + 1) 개)
        - Leaf 노드들은 개수가 가장 많지만, Heapify 거의 수행되지 않음
            - 이러한 이유로 전체 합은 O(n) 

    3. deleteMax 의 역할 : 시간 복잡도 계산
    -   처음 힙 크기 : n  to  마지막 1 까지 감소
    - 각 단계에서 deleteMax 의 시간은 힙의 높이, 즉 O(logK) (K 는 *현재 힙 크기*를 의미)
    

## 정렬 알고리즘 비교
#### 병합 정렬 (Merge sort) 
-  항상 **O(nlogn)** 보장하지만 추가 메모리 O(n) 필요
    -  추가적인 temp 메모리 할당하므로 실제로는 *느림*
#### 힙 정렬 (Heap sort)
-  항상 **O(nlogn)** 보장
-  추가 메모리 거의 없음
-  힙의 *구조적 제약* 때문에 실제 상수 계수가 크고 캐시활용이 나쁨
#### 퀵 정렬 (Quick sort)
-  평균 **O(nlogn)**, 최악 **O(n^2)**
-  분할 과정에서 순차적으로 탐색하면서 단순  비교와 교횐민 수행함 (메모리 추가 할당 거의 없다는 장점 존재)
    -  제자리 정렬 (in-place sort)