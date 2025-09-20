# 동시성 & 성능 최적화

## 1. 스레드 모델 개요
| 모델 | 설명 | 사용 |
|------|------|----|
| Platform Threads | OS 스레드 매핑 | 전통적 Java | 
| Virtual Threads (JDK 21) | 경량 스케줄링 | 대량 I/O 동시성 |
| 이벤트 루프 (NIO) | Selector 기반 | Netty, Reactor |

## 2. Executor 프레임워크
```java
ExecutorService pool = Executors.newFixedThreadPool(8);
Future<Integer> f = pool.submit(() -> compute());
Integer v = f.get();
```
권장: `Executors` 대신 `ThreadPoolExecutor` 명시적 튜닝.

| 타입 | 특징 | 용도 |
|------|------|-----|
| Fixed | 고정 스레드 | CPU 바운드 |
| Cached | 동적 증가 | 짧은 비동기 Task |
| Scheduled | 지연/주기 실행 | 배치/주기 모니터 |
| Virtual | 수천~수만 경량 | I/O 바운드 |

## 3. ThreadPoolExecutor 파라미터
| 항목 | 의미 | 가이드 |
|------|------|--------|
| corePoolSize | 유지 스레드 | CPU 코어 수 근사 |
| maximumPoolSize | 최대 스레드 | I/O 비율 고려 |
| keepAliveTime | 초과 스레드 생존 | I/O 폭증 대비 |
| workQueue | 대기 큐 | `LinkedBlockingQueue` 기본 |
| RejectionHandler | 포화 처리 | 로그/백프레셔 |

## 4. CompletableFuture
```java
CompletableFuture<Integer> r = CompletableFuture.supplyAsync(this::load)
  .thenApply(v -> v * 2)
  .exceptionally(ex -> fallback());
```
조합: `allOf`, `anyOf`, `thenCombine`.

## 5. 동기화 도구
| 도구 | 사용 | 특징 |
|------|------|------|
| synchronized | 임계영역 | 간단/가시성 보장 |
| ReentrantLock | 고급 락 | 타임아웃, 공정성 |
| ReadWriteLock | 읽기 다수 | 쓰기 적음 시 유리 |
| StampedLock | 낙관적 읽기 | 고성능 읽기 경합 완화 |
| Semaphore | 자원 카운트 | 커넥션 슬롯 |
| CountDownLatch | 일회성 대기 | 초기화 완료 대기 |
| CyclicBarrier | 반복 동기화 | 병렬 단계 처리 |
| Phaser | 가변 파티 | 동적 참여 |
| Atomic* | lock-free 연산 | CAS 기반 |

## 6. 가시성 & 메모리 모델
| 개념 | 설명 | 예 |
|------|------|----|
| Happens-Before | 재정렬 금지 순서 보장 | 락 획득/해제, volatile |
| volatile | 가시성 + 순서 | 플래그, 단순 상태 | 
| final | 안전한 초기화 | 불변 객체

## 7. 경쟁 조건 & 데드락
| 문제 | 설명 | 대응 |
|------|------|----|
| Race Condition | 순서 비결정 | 원자적 갱신(Atomic), 락 |
| Deadlock | 상호 잠금 | 락 순서 통일, 타임아웃 |
| Livelock | 계속 양보 | 재시도 지연 |
| Starvation | 자원 편향 | 공정 락, 우선순위 튜닝 |

## 8. 병렬 스트림 vs 풀 직접 사용
| 항목 | 병렬 스트림 | 커스텀 풀 |
|------|-------------|-----------|
| 제어 | 제한적 | 세밀 튜닝 |
| 디버깅 | 어려움 | 명시적 | 
| 작업 크기 | 큰 컬렉션 유리 | 다양 |
| 블로킹 호출 | 위험 (ForkJoinPool) | 별도 풀 |

## 9. Virtual Thread 간단 예 (JDK 21)
```java
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
  List<Callable<String>> tasks = IntStream.range(0, 10_000)
    .mapToObj(i -> (Callable<String>) () -> fetch(i))
    .toList();
  executor.invokeAll(tasks);
}
```
I/O 대기 많은 서비스에 적합.

## 10. 락 경합 줄이기
| 기법 | 설명 | 예 |
|------|------|----|
| 락 범위 축소 | 임계영역 최소화 | 계산 밖으로 이동 |
| 분할(Sharding) | 여러 락으로 분산 | Stripe Map |
| Copy-on-write | 읽기 다수, 쓰기 적음 | 설정 스냅샷 |
| 캐시 지역성 | 데이터 구조 최적 | 배열 연속성 |

## 11. 성능 측정 (JMH)
```java
@State(Scope.Thread)
public class Bench {
  @Benchmark
  public int sum(){ int s=0; for(int i=0;i<1000;i++) s+=i; return s; }
}
```
실행: `mvn clean install` 후 `java -jar target/benchmarks.jar`

## 12. GC 튜닝 개요
| Collector | 장점 | 사용 |
|-----------|------|------|
| Serial | 단순 | 작은 애플리케이션 |
| Parallel | Throughput | 배치 처리 |
| G1 | 짧은 중단 | 서버 기본 |
| ZGC | 매우 낮은 지연 | 초저지연 |
| Shenandoah | 지역성 + 짧은 중단 | 대용량 힙 |

옵션 예: `-Xms512m -Xmx512m -XX:+UseG1GC -XX:MaxGCPauseMillis=200`

## 13. 프로파일링 단계
| 단계 | 도구 | 목적 |
|------|------|-----|
| 샘플링 | `jfr`, async-profiler | 핫스팟 함수 |
| 메모리 | `jmap`, `jcmd GC.heap_info` | 힙 구조 |
| 스레드 덤프 | `jstack` | 교착/대기 |
| JFR | Java Flight Recorder | 종합 이벤트 |

## 14. 배압(Backpressure) & 큐
| 상황 | 문제 | 해결 |
|------|------|------|
| 생산 > 소비 | 메모리 증가 | 큐 용량 제한 |
| 처리 지연 | 지연 확산 | 드롭/샘플링 |
| 폭주 요청 | 서버 포화 | 토큰 버킷, 서킷 브레이커 |

## 15. 동시 컬렉션 패턴
```java
ConcurrentMap<String, LongAdder> counters = new ConcurrentHashMap<>();
public void incr(String k){ counters.computeIfAbsent(k, _ -> new LongAdder()).increment(); }
```
`LongAdder`: 다중 셀 기반 contention 감소.

## 16. Reactive Streams 간단 비교
| 구현 | 특징 |
|------|------|
| Project Reactor | Mono/Flux, 연산자 풍부 |
| RxJava | 관찰자 패턴 확장 | 
| Akka Streams | Actor 기반 backpressure |

## 17. 체크리스트
- [ ] 블로킹 호출 전용 스레드 풀 분리
- [ ] ThreadPool 사이즈 적절성 모니터링
- [ ] LongAdder/Accumulator로 카운터 경합 최소화
- [ ] JFR/프로파일로 병목 식별 후 최적화
- [ ] Virtual Thread 적용 가성비 검토(I/O 바운드)

---
추가 원하면 GC 상세 튜닝/Reactive 예제 확장 가능.
