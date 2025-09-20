# OOP 설계 (클래스/인터페이스/추상화)

## 1. 객체지향 4대 기둥
| 개념 | 요약 | 포인트 | 안티패턴 |
|------|------|-------|---------|
| 캡슐화 | 내부 상태 은닉 + 인터페이스로 제어 | 불변/방어적 복사 | Getter 남용, Data Class화 |
| 상속 | is-a 관계 재사용 | 추상화/다형성 활용 | 과도한 계층, Fragile Base |
| 다형성 | 공통 인터페이스로 다른 구현 교체 | 전략/정책 분리 | if-else 타입 분기 |
| 추상화 | 복잡성 감추고 본질 표현 | Port/Adapter, Interface | 세부 누수, God Object |

## 2. 클래스 vs 인터페이스 vs 추상 클래스
| 요소 | 목적 | 언제 사용 | 특징 |
|------|------|-----------|------|
| 클래스 | 구체 구현 | 실체 객체 | 상태 + 행동 |
| 인터페이스 | 기능 계약 | 다형성, 확장 | 다중 구현 가능, 상태 없음 |
| 추상 클래스 | 부분 구현 + 공통 상태 | 템플릿 메서드, 기본 로직 공유 | 단일 상속, protected 활용 |

### 결정 가이드
1. 상속보다 합성: "~를 가진(has-a)" 관계면 필드로 주입.
2. 공통 상태/부분 구현이 필요하면 추상 클래스, 오직 규약이면 인터페이스.
3. 교체 가능성이 높은 협력은 인터페이스로 분리 (ex: PaymentGateway).

## 3. 의존 역전 & 인터페이스 분할
```java
public interface NotificationSender { void send(String to, String message); }
public class EmailSender implements NotificationSender { ... }
public class SmsSender implements NotificationSender { ... }

public class AlertService {
  private final NotificationSender sender;
  public AlertService(NotificationSender sender) { this.sender = sender; }
  public void critical(String user, String msg){ sender.send(user, "[CRITICAL] " + msg); }
}
```
- 고수준 모듈(AlertService)은 추상(NotificationSender)에 의존
- 런타임에 구현체 교체 (DI 컨테이너 or 수동 주입)

## 4. 불변 객체 (Immutable) 설계
```java
public final class Money {
  private final long amount; // cents
  private final String currency;
  private Money(long amount, String currency){ this.amount = amount; this.currency = currency; }
  public static Money of(long amount, String currency){ return new Money(amount, currency); }
  public Money add(Money other){ ensureSameCurrency(other); return new Money(this.amount + other.amount, currency); }
  private void ensureSameCurrency(Money other){ if(!this.currency.equals(other.currency)) throw new IllegalArgumentException(); }
  public long amount(){ return amount; }
}
```
장점: 스레드 안전, 예측 가능. 단점: 생성 비용 증가 → 캐싱/팩토리 고려.

## 5. Builder 패턴 적용 시점
| 조건 | Builder 고려 |
|------|--------------|
| 파라미터 4개 이상 & 선택적 | YES |
| 생성자 오버로드 다수 | YES |
| 불변 객체 + 가독성 | YES |
| 단순 1~2개 필드 | NO |

## 6. 도메인 모델링 패턴
| 패턴 | 설명 | 예 |
|------|------|----|
| 엔티티 | 식별자 ID, 라이프사이클 | Order, User |
| Value Object | 값 동등성, 불변 | Money, DateRange |
| Aggregate Root | 경계 내 일관성 책임 | Order (OrderLine 포함) |
| Repository | 집합 조회/저장 추상화 | OrderRepository |
| Service (Domain/App) | 계산/조합 로직 | PricingPolicyService |

## 7. 설계 품질 원칙 (SOLID 요약)
| 원칙 | 핵심 요약 | 위반 신호 |
|------|-----------|-----------|
| SRP | 한 클래스 하나의 이유 | 변경 사유 다수 |
| OCP | 확장은 열려, 변경 닫힘 | switch로 타입 분기 |
| LSP | 하위형은 행태 보장 | "지원 안함" 예외 |
| ISP | 클라이언트 특화 인터페이스 | 비어있는 구현 다수 |
| DIP | 추상에 의존 | 구체 new 직접 생성 |

## 8. 예: 전략(Strategy) 사용 전/후
Before
```java
class PriceCalculator {
  double calc(String type, double amount){
    if(type.equals("CARD")) return amount * 0.95;
    if(type.equals("COUPON")) return amount - 5;
    return amount;
  }
}
```
After
```java
interface DiscountPolicy { double apply(double amount); }
class CardDiscount implements DiscountPolicy { public double apply(double a){ return a * 0.95; }}
class CouponDiscount implements DiscountPolicy { public double apply(double a){ return a - 5; }}
class PriceCalculator {
  private final DiscountPolicy policy;
  PriceCalculator(DiscountPolicy policy){ this.policy = policy; }
  double calc(double amount){ return policy.apply(amount); }
}
```

## 9. 템플릿 메서드 vs 전략 선택 기준
| 상황 | 템플릿 메서드 | 전략 |
|------|---------------|------|
| 상속 허용 | 필요 | 필요 없음 |
| 알고리즘 골격 고정 | YES | 부분만 교체 | 
| 런타임 교체 | 어려움 | 쉬움 |
| 구현 수 증가 | 추상 클래스 폭발 | 인터페이스 구현 추가 |

## 10. 리팩터링 신호 (Code Smell)
| Smell | 특징 | 개선 |
|-------|------|------|
| Long Method | 30+ 라인 복합 책임 | 메서드 추출, 위임 |
| Feature Envy | 다른 객체 데이터 남용 | 로직 이동 |
| Data Class | Getter/Setter만 | 캡슐화, 불변 전환 |
| God Class | 다기능 집중 | 책임 분할 |
| Primitive Obsession | 원시타입 범람 | VO 도입 |
| Switch on Type | 타입 분기 반복 | 다형성 |

## 11. 예외 설계 지침 (간단 미리보기)
- 비즈니스 규칙 위반: 커스텀 언체크 예외 (ex: `InsufficientBalanceException`)
- 복구 가능 IO: 체크 예외 유지
- 메서드 시그니처 `throws` 남발 금지 → 도메인 계층 단순화.

## 12. 테스트 용이성 설계
| 기법 | 목적 | 구현 |
|------|------|------|
| 의존 주입 | Mock/Stubs 치환 | 생성자 주입 |
| 인터페이스 분리 | 불필요 협력 제거 | 좁은 Port |
| 불변객체 | 예측성 | 상태 변이 없음 |
| 순수 함수 | 결정적 결과 | 사이드이펙트 분리 |

## 13. 도구 & 검증
- SpotBugs: 상속/Equals/HashCode 문제 감지
- ArchUnit: 계층 의존 규칙 테스트
- ErrorProne: 공통 실수 컴파일 단계 검출

## 14. Check 리스트
- [ ] if/else 타입 분기 다형성으로 변환
- [ ] 불변 Value Object 도입 고려
- [ ] 생성자 대신 정적 팩토리 적절히 사용
- [ ] DIP 준수(비즈니스 로직 new 금지)
- [ ] 테스트에서 Mock 치환 가능 구조

---
추가로 DDD Tactical Patterns (Factory, Domain Event 등) 원하면 확장 가능합니다.
