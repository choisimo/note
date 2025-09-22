## 힙 정렬 (Max Heap 기반)

### 1. 힙(Heap) 기본 성질
- **완전 이진 트리(Complete Binary Tree)**: 마지막 레벨을 제외한 모든 레벨이 가득 차 있고, 마지막 레벨은 왼쪽부터 채워짐.
- **Max Heap 조건**: 모든 부모 노드 값 ≥ 자식 노드 값.
- 배열 인덱스 표현:
  - Root: index 0
  - Parent(i) = (i-1)//2
  - Left(i) = 2*i + 1, Right(i) = 2*i + 2

### 2. 완전 이진 트리를 배열로 쓰는 이유
| 항목 | 장점 |
|------|------|
| 포인터 불필요 | 부모/자식 인덱스 산술식으로 접근 |
| 공간 효율 | Node 객체/링크 오버헤드 제거 |
| 캐시 이점 | 연속 메모리 (단, 힙 정렬 과정 후반엔 감소) |

### 3. Build Heap (Heapify Bottom-Up)
마지막 비리프(Internal) 노드부터 0까지 내려오며 `percolateDown` (혹은 `siftDown`) 수행.
- 마지막 비리프 노드 index = (n-2)//2
- 전체 비용은 O(n). (높이별 작업량 합산 시 등비수열 수렴)

```text
buildHeap(A, n):
  for i from (n-2)//2 downto 0:
    percolateDown(A, n, i)
```

### 4. Percolate Down (Sift Down)
```text
percolateDown(A, heapSize, k):
  while true:
    left  = 2*k + 1
    right = 2*k + 2
    if left >= heapSize: break

    larger = left
    if right < heapSize and A[right] > A[left]:
      larger = right

    if A[k] < A[larger]:
      swap(A[k], A[larger])
      k = larger
    else:
      break
```

### 5. Heap Sort 과정
1. Build Heap (Max Heap)
2. 루트(최대값) ↔ 마지막 원소 교환, heapSize 감소
3. 감소된 heapSize 범위에서 root 재정렬(percolateDown)
4. heapSize == 1 될 때까지 반복

```text
heapSort(A, n):
  buildHeap(A, n)
  for end from n-1 downto 1:
    swap(A[0], A[end])
    percolateDown(A, end, 0)   # end가 새로운 heapSize
```

### 6. deleteMax 관점
```text
deleteMax(A, heapSize):
  maxVal = A[0]
  A[0] = A[heapSize-1]
  heapSize--
  percolateDown(A, heapSize, 0)
  return maxVal
```
Heap Sort는 `deleteMax`를 반복하여 정렬된 배열을 뒤에서부터 채우는 과정과 동일.

### 7. 시간 복잡도 상세
| 단계 | 복잡도 |
|------|--------|
| buildHeap | O(n) |
| n번 deleteMax | n * O(log n) = O(n log n) |
| 전체 | O(n log n) (best=avg=worst 동일) |

Build 단계가 O(n) 인 이유:
- 높이 h인 노드 수 ≈ n / 2^{h+1}, 각 노드 percolateDown 최대 O(h)
- Σ (n / 2^{h+1}) * h  = O(n)

### 8. 공간/특징
| 항목 | 힙 정렬 |
|------|---------|
| In-place | 예 (O(1) 추가 메모리) |
| 안정성 | 기본 비안정 (동일 값 상대 순서 변함) |
| 캐시 지역성 | 낮음 (트리 구조 점프) |
| 최악 성능 | O(n log n) 보장 |

### 9. 힙 정렬 실무 사용 주의
- 병합/퀵 대비 캐시 효율 낮아 실제 시간 느릴 수 있음.
- 안정성이 필요한 경우 다른 알고리즘 고려.
- 부분 정렬(Top-K)에서는 전체 정렬 대신 사이즈 K 힙 활용이 더 효율적 (O(n log K)).

### 10. 예시 (단계 축약)
A = [13, 5, 7, 2, 9]
1) buildHeap → [13, 9, 7, 2, 5]
2) swap root & last → [5, 9, 7, 2, 13]; heapSize=4 → percolateDown → [9, 5, 7, 2, 13]
3) swap → [2, 5, 7, 9, 13]; heapSize=3 → percolateDown → [7, 5, 2, 9, 13]
4) swap → [2, 5, 7, 9, 13]; heapSize=2 → percolateDown → [5, 2, 7, 9, 13]
5) swap → [2, 5, 7, 9, 13] (완료)

---
## 주요 정렬 알고리즘 비교 (개념 재정리)

| 알고리즘 | 평균 | 최악 | 추가 메모리 | 안정성 | 특징 요약 |
|----------|------|------|------------|--------|-----------|
| 선택 정렬 | O(n^2) | O(n^2) | O(1) | 비안정 | 교환 최소, 항상 느림 |
| 버블 정렬 | O(n^2) | O(n^2) | O(1) | 안정 | 거의 정렬된 경우 약간 이득 |
| 삽입 정렬 | O(n^2) | O(n^2) | O(1) | 안정 | 거의 정렬/소규모 입력에 강함 |
| 쉘 정렬 | ~O(n^{1.3}) | O(n^2) | O(1) | 비안정 | Gap 시퀀스에 따라 성능 편차 |
| 합병 정렬 | O(n log n) | O(n log n) | O(n) | (구현 따라) 안정 | Divide & Conquer, 외부정렬 활용 |
| 힙 정렬 | O(n log n) | O(n log n) | O(1) | 비안정 | 최악 보장, 캐시 약점 |
| 퀵 정렬 | O(n log n) | O(n^2) | O(log n) stack | 비안정 | 평균 가장 빠름, 분할 품질 중요 |
| 계수 정렬 | O(n + k) | O(n + k) | O(n + k) | 안정 | k 범위 작을 때 강력 |
| 기수 정렬 | O(k n) | O(k n) | O(n + b) | 안정 | 고정 길이 키, 비교 안함 |
| 버킷 정렬 | O(n) (평균) | O(n^2) | O(n + b) | (내부정렬 의존) | 균등 분포 가정 |

### 선택 포인트
| 조건 | 추천 |
|------|------|
| 안정성 + 큰 n | Merge, (Timsort 파생) |
| 메모리 제약 | Heap, In-place Quick (주의) |
| 평균 속도 | Quick (피벗 개선 + 하이브리드) |
| 값 범위 작음 | Counting / Radix |
| Top-K | Heap(Partial) / Quickselect |
| 거의 정렬됨 | Insertion / Adaptive (Timsort) |

### 11. 용어 정리
| 용어 | 의미 |
|------|------|
| Stable | 동일 키 상대 순서 유지 |
| In-place | O(1) 추가 공간 (or 매우 작음) |
| Divide & Conquer | 분할 → 정복 → 결합 패턴 |
| Partition | 배열을 피벗 기준 두 부분으로 나누는 과정 |
| Heapify | 힙 속성 재구축 (Sift Up/Down) |
| External Sort | 메모리에 못 올리는 대용량 정렬 (Disk/Merge 단계) |

### 12. Stirling 근사와 n! 관련 (참고)
정렬 비교 하한 도출에 쓰이는 n! ~ sqrt(2πn) (n/e)^n → log2(n!) ≈ n log2 n - 1.44 n.
- 비교 정렬은 최소 O(log2(n!)) ≈ O(n log n) 비교 필요.
- 힙/퀵/병합이 O(n log n) 에 수렴하는 이유.

---
(End of sort-3 확장)
