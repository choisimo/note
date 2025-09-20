# 디자인 패턴 & SOLID 요약

## 1. SOLID 상세
| 원칙 | 확장 | 핵심 | 위반 신호 |
|------|------|------|-----------|
| SRP | Single Responsibility | 변경 이유 하나 | 다중 기능 메서드 덩어리 |
| OCP | Open Closed | 확장 용이, 수정 최소 | switch-case 타입 분기 |
| LSP | Liskov Substitution | 하위형 대체 가능 | override 후 예외 던짐 |
| ISP | Interface Segregation | 좁은 인터페이스 | "빈 구현" 다수 |
| DIP | Dependency Inversion | 추상 의존 | new 직접 생성 |

## 2. 분류별 GoF 패턴 목록
| 생성 | 구조 | 행위 |
|------|------|------|
| Factory Method | Adapter | Strategy |
| Abstract Factory | Bridge | Template Method |
| Builder | Composite | Observer |
| Prototype | Decorator | Command |
| Singleton | Facade | Iterator |
| | Flyweight | Mediator |
| | Proxy | State |
| | | Chain of Responsibility |
| | | Visitor |
| | | Memento |
| | | Interpreter |

## 3. 대표 패턴 요약 (실무 활용 빈도 위주)
| 패턴 | 목적 | 사용 시점 | 예 |
|------|------|----------|----|
| Strategy | 알고리즘 교체 | 조건 분기 다수 | 결제 할인 정책 |
| Template Method | 알고리즘 골격 고정 | 공통 단계 + 가변 훅 | 데이터 ETL |
| Builder | 복잡 객체 생성 | 많은 선택적 파라미터 | HTTP 요청 빌더 |
| Factory Method | 생성 위임 | 하위 타입 결정 연기 | Parser 생성 |
| Abstract Factory | 관련 객체군 생성 | UI 테마 세트 | DAO + Tx 관리 |
| Singleton | 전역 1개 인스턴스 | 비용 큰 공유 자원 | 커넥션 풀(그러나 DI 권장) |
| Facade | 복잡 서브시스템 단순화 | 다중 API 집약 | 서드파티 SDK 래퍼 |
| Adapter | 인터페이스 호환 | 레거시/외부와 연결 | 서로 다른 통화 API |
| Decorator | 기능 동적 합성 | 런타임 부가 기능 | 캐시 + 로깅 + 권한 |
| Observer | 이벤트 통지 | 느슨한 결합 | Domain Event, GUI |
| Command | 요청 캡슐화 | undo/큐잉 | 작업 큐, 매크로 |
| State | 상태별 행태 분리 | if/else 상태 폭발 | 주문 상태 전이 |
| Proxy | 접근 제어/지연 | 원격/캐싱/보안 | Lazy Load, AOP |
| Chain of Responsibility | 순차 처리 | 필터 파이프라인 | 인증 → 검증 → 실행 |

## 4. Strategy vs Template 비교
| 구분 | Strategy | Template Method |
|------|----------|-----------------|
| 구조 | 합성 | 상속 |
| 교체 시점 | 런타임 | 컴파일 시 | 
| 재사용 | 조합 용이 | 상속 계층 제한 |
| 다형성 확장 | 구현 클래스 추가 | 추상 클래스 확장 |

## 5. Decorator 구현 예
```java
interface Notifier { void send(String msg); }
class BasicNotifier implements Notifier { public void send(String msg){ System.out.println(msg); }}
class SlackDecorator implements Notifier {
  private final Notifier delegate;
  SlackDecorator(Notifier d){ this.delegate = d; }
  public void send(String msg){ delegate.send(msg); postToSlack(msg); }
  private void postToSlack(String msg){ /* ... */ }
}
```
조합: `new SlackDecorator(new BasicNotifier())`

## 6. State 패턴 예
```java
interface OrderState { void next(Order ctx); String name(); }
class PaymentPending implements OrderState {
  public void next(Order o){ o.changeState(new Paid()); }
  public String name(){ return "PENDING"; }
}
class Paid implements OrderState { public void next(Order o){ o.changeState(new Shipped()); } public String name(){ return "PAID"; }}
class Shipped implements OrderState { public void next(Order o){ /* terminal */ } public String name(){ return "SHIPPED"; }}
class Order {
  private OrderState state = new PaymentPending();
  void changeState(OrderState s){ this.state = s; }
  void advance(){ state.next(this); }
  String status(){ return state.name(); }
}
```

## 7. Observer (도메인 이벤트) 예
```java
class DomainEventPublisher {
  private final List<Consumer<Object>> subscribers = new CopyOnWriteArrayList<>();
  public void subscribe(Consumer<Object> c){ subscribers.add(c); }
  public void publish(Object event){ subscribers.forEach(c -> c.accept(event)); }
}
record OrderCreatedEvent(String orderId){}
```

## 8. Proxy 패턴 (캐싱)
```java
interface ProductCatalog { Product find(String id); }
class DbProductCatalog implements ProductCatalog { public Product find(String id){ /* DB */ return null; }}
class CachingCatalogProxy implements ProductCatalog {
  private final ProductCatalog target; private final Map<String, Product> cache = new ConcurrentHashMap<>();
  CachingCatalogProxy(ProductCatalog t){ this.target = t; }
  public Product find(String id){ return cache.computeIfAbsent(id, target::find); }
}
```

## 9. 패턴 선택 기준 질문
| 질문 | 의미 | 예 |
|------|------|----|
| 변경이 어디서 가장 자주 발생? | 유연성 초점 위치 | 할인 정책 → Strategy |
| 조건 분기가 반복되는가? | 다형성 필요 | 상태 전이 → State |
| 객체 생성이 복잡한가? | 빌더/팩토리 고려 | Config 의존 많음 |
| 외부 시스템 복잡? | Facade/Adapter | 다수 SDK 래핑 |
| 기능 조합/순서? | Decorator/Chain | 필터 파이프라인 |

## 10. 안티패턴 (패턴 남용)
| 이름 | 문제 | 대안 |
|------|------|------|
| Singleton 남용 | 전역 상태, 테스트 어려움 | DI 컨테이너, 스코프 제한 |
| Abstract Factory 남발 | 불필요 추상 | 단순 new |
| 지나친 Layering | 얇은 위임 사슬 | 직접 호출 단순화 |
| 패턴 따라 만들기 | 과한 추상 | YAGNI 적용 |

## 11. 모던 Java + 패턴 대체
| 전통 패턴 | 현대 대체 | 비고 |
|-----------|-----------|------|
| Iterator (명시) | Stream API | 내부 반복 |
| Observer | Flow API, Reactor | 비동기 스트림 |
| Command | 람다(Runnable) | 간단 요청 캡슐화 |
| Factory Method | 정적 팩토리, Supplier | 간단 생성 |

## 12. 코드 리뷰 체크리스트
- [ ] 조건 분기 다형성 전환 가능?
- [ ] 상태 폭발 패턴(State) 고려?
- [ ] 전역 싱글톤 제거/DI 적용?
- [ ] Decorator/Chain 과도한 중첩?
- [ ] 패턴 도입 근거(변경 빈도) 명확?

---
원하면 각 패턴 별 UML 다이어그램 예시 추가 가능.
