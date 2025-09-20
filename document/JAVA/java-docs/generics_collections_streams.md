# 제네릭, 컬렉션, 스트림 고급 가이드

## 1. 제네릭 핵심
| 요소 | 설명 | 예 |
|------|------|----|
| 타입 매개변수 | 클래스/메서드 재사용을 위한 형식 변수 | `class Box<T>` |
| 바운드 | 특정 상위 타입 제한 | `<T extends Number>` |
| 다중 바운드 | 인터페이스 + 클래스 조합 | `<T extends Number & Comparable<T>>` |
| 와일드카드 | 불공변성 우회 읽기 전용 | `List<? extends Number>` |
| PECS | Producer Extends, Consumer Super | `? extends T` / `? super T` |

### 불공변성
`List<String>` != `List<Object>` (대입 불가) → 컴파일 타입 안전.

### 와일드카드 규칙
| 상황 | 선언 | 의미 |
|------|------|------|
| 읽기 전용 (producer) | `List<? extends T>` | T 또는 하위 타입; get OK, add 불가(null 제외) |
| 쓰기 전용 (consumer) | `List<? super T>` | T 상위 타입; add T OK, get Object |
| 불필요하면 | `List<T>` | 명확한 타입 |

## 2. 제네릭 메서드 예
```java
public static <T extends Comparable<T>> T max(List<T> list){
  if(list.isEmpty()) throw new IllegalArgumentException();
  T m = list.get(0);
  for(T e : list){ if(e.compareTo(m) > 0) m = e; }
  return m;
}
```

## 3. 타입 소거(Type Erasure) 영향
| 현상 | 결과 | 대응 |
|------|------|------|
| 런타임 제네릭 정보 제거 | `List<String>` → `List` | 리플렉션(TypeToken) 사용 |
| `new T()` 불가 | 생성자 직접 호출 불가 | Supplier<T> 주입 |
| 배열 생성 불가 | `new T[10]` X | `List<T>` 사용 or `@SuppressWarnings` + 캐스트 |

## 4. 컬렉션 선택 가이드
| 요구사항 | 추천 | 이유 |
|----------|------|-----|
| 인덱스 랜덤 접근 | `ArrayList` | O(1) get |
| 중간 삽입/삭제 다수 | `LinkedList` | 양방향 노드 |
| 중복 불가 + 정렬 | `TreeSet` | 정렬+검색 O(log n) |
| 해시 기반 중복 불가 | `HashSet` | 평균 O(1) |
| Key-Value 캐시 | `LinkedHashMap` | 순서 유지 |
| 정렬 Map | `TreeMap` | 범위 조회 |
| 고성능 동시 Map | `ConcurrentHashMap` | 세그먼트 락/CPU 친화 |
| Enum 키 | `EnumMap` | 배열 기반 빠름 |

## 5. 불변/방어적 컬렉션
```java
List<String> names = List.of("a", "b"); // 변경 불가
List<String> copy = Collections.unmodifiableList(new ArrayList<>(names));
```

## 6. Stream 핵심 파이프라인
```
source → intermediate(0..n) → terminal
```
- Lazy, Stateless/Stateful (sorted, distinct, limit)
- 재사용 불가(한 번 소비)

### 예시
```java
double avg = employees.stream()
  .filter(e -> e.active())
  .mapToInt(Employee::score)
  .average()
  .orElse(0);
```

## 7. Collector 활용
```java
Map<String, List<Employee>> byDept = employees.stream()
  .collect(Collectors.groupingBy(Employee::department));

double total = employees.stream()
  .collect(Collectors.summingDouble(Employee::salary));
```
커스텀 Collector 필요: 성능 튜닝(병렬) or 특수 누적 로직.

## 8. 병렬 스트림 주의
| 위험 | 설명 | 완화 |
|------|------|------|
| 공유 상태 경쟁 | 외부 변경 | 불변/로컬 변수만 |
| 순서 비용 | ordering 유지 비용 | unordered() 사용 |
| 데이터 크기 | 작을 경우 오버헤드 | 충분히 큰 컬렉션 |
| Spliterator 품질 | 비균등 분할 | 정렬된 ArrayList 유리 |

## 9. Optional 패턴
```java
Optional<User> user = repo.find(id);
String email = user.map(User::profile)
                   .map(Profile::email)
                   .filter(e -> e.endsWith(".com"))
                   .orElse("N/A");
```
- 필드로 보관 금지 (직렬화/의미 불명확)
- 컬렉션 요소로 Optional 지양 → 빈 컬렉션 사용.

## 10. 성능 팁
| 항목 | 조언 |
|------|------|
| 박싱/언박싱 | primitive stream (`IntStream`) 사용 |
| 수집기 중복 | 다단계 grouping 최소화 |
| toList() | Java 16+ 고정 리스트 (수정 필요시 new ArrayList) |
| Stream 남용 | 단순 for 루프가 더 명확/빠름일 수 있음 |

## 11. Concurrent 컬렉션
| 용도 | 컬렉션 | 특징 |
|------|--------|------|
| 고빈도 읽기/쓰기 | `ConcurrentHashMap` | 락 분할, size 근사 |
| 작업 큐 | `LinkedBlockingQueue` | 생산자/소비자 |
| 우선순위 작업 | `PriorityBlockingQueue` | 정렬 큐 |
| 지연 작업 | `DelayQueue` | 시간 기반 poll |
| 중복 없는 set | `ConcurrentSkipListSet` | 정렬 + 동시 |

## 12. Map 병합 패턴
```java
map.merge(key, 1, Integer::sum); // 카운팅
cache.computeIfAbsent(id, loader::load);
```

## 13. 패턴: Collector 없이 누적
```java
int sum = 0;
for(int v : list){ sum += v; }
```
심플/핫패스에서는 루프 우선.

## 14. 실수 & 안티패턴
| 항목 | 문제 | 대안 |
|------|------|------|
| Stream 안에서 I/O | 지연 + 예외 처리 혼란 | 사전 로드 |
| 무분별한 parallel() | 속도 저하 | 프로파일 후 적용 |
| Optional.get() 남용 | NoSuchElementException | orElse / orElseThrow |
| raw 타입 사용 | 타입 안전성 상실 | 제네릭 명시 |

## 15. 체크리스트
- [ ] raw 타입 제거
- [ ] Optional 필드 사용 금지
- [ ] primitive stream 고려(Int/Long/Double)
- [ ] parallel() 도입 전 JMH 측정
- [ ] computeIfAbsent/merge 적극 활용

---
필요 시 JMH 벤치마크 작성 예제 추가 가능.
