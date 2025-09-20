# 예외 처리 & 로깅 가이드

## 1. 예외 기본 전략
| 구분 | 체크 예외(Checked) | 언체크 예외(Runtime) |
|------|--------------------|-----------------------|
| 사용 목적 | 호출 측에서 반드시 처리 필요(복구 가능) | 프로그래밍 오류/비즈니스 규칙 위반 |
| 예 | `IOException`, `SQLException` | `IllegalArgumentException`, `NullPointerException` |
| 전환 | 빈번한 래핑 지양 | 의미 명확 커스텀 도입 |

권장: 비즈니스 규칙 위반은 언체크 커스텀 예외 사용 → 서비스 상위 단에서 공통 처리.

## 2. 예외 설계 지침
| 원칙 | 설명 | 예 |
|------|------|----|
| 의미 있는 타입 | 스택트레이스만으로 의도 파악 | `InsufficientBalanceException` |
| 메시지 컨텍스트 | 실패 인자 포함 | `"balance=%d, required=%d"` |
| 중복 로깅 금지 | 한 에러 한 번만 | Controller Advice에서 단일 로깅 |
| unwrap/translate | 기술 예외 → 도메인 예외 | `DataAccessException` → `OrderNotFoundException` |

## 3. 커스텀 예외
```java
public class InsufficientBalanceException extends RuntimeException {
  private final long balance; private final long required;
  public InsufficientBalanceException(long balance, long required){
    super("Insufficient balance: balance=" + balance + ", required=" + required);
    this.balance = balance; this.required = required;
  }
  public long getBalance(){ return balance; }
}
```

## 4. 예외 전환 패턴
```java
try {
  repository.save(order);
} catch (SQLException e){
  throw new PersistenceException("order save failed id=" + order.id(), e);
}
```
- 원인 보존(`cause`) 필수.

## 5. 로깅 레벨 가이드
| 레벨 | 용도 | 예 |
|------|------|----|
| ERROR | 복구 불가/사용자 영향 | 결제 실패, 데이터 손실 |
| WARN | 비정상 징후, 자동 복구 가능 | 재시도, fallback 사용 |
| INFO | 상태 변화, 비즈니스 이벤트 | 주문 생성, 배치 시작 |
| DEBUG | 상세 흐름 | 쿼리 파라미터, 브랜치 분기 |
| TRACE | 매우 세밀 | 루프 내부 값 |

## 6. SLF4J 사용 패턴
```java
private static final Logger log = LoggerFactory.getLogger(OrderService.class);
log.info("order created id={} amount={}", orderId, amount);
log.error("payment failed id={}", orderId, ex); // ex는 마지막 파라미터
```
- 문자열 연결 대신 `{}` 플레이스홀더
- 예외 스택: 마지막 인자에 Throwable 전달

## 7. 민감정보(PII) 마스킹
| 데이터 | 처리 | 예 |
|--------|------|----|
| 카드번호 | 마지막 4자리만 | `****-****-****-1234` |
| 비밀번호/토큰 | 출력 금지 | 제거 |
| 이메일 | 일부 마스킹 | `u***@domain.com` |

## 8. 로깅 구성 (Logback 단순 예)
`logback.xml`
```xml
<configuration>
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger - %msg%n</pattern>
    </encoder>
  </appender>
  <logger name="org.hibernate.SQL" level="DEBUG" />
  <root level="INFO">
    <appender-ref ref="STDOUT" />
  </root>
</configuration>
```

## 9. 구조적 로깅 (JSON)
- LogstashEncoder 사용 or `application-log.json` 출력
- 키-값 컨텍스트: MDC 활용
```java
MDC.put("orderId", orderId);
log.info("order processed");
MDC.clear();
```

## 10. 전역 예외 처리 (Spring 예시)
```java
@RestControllerAdvice
class GlobalExceptionHandler {
  @ExceptionHandler(InsufficientBalanceException.class)
  ResponseEntity<ApiError> handle(InsufficientBalanceException e){
    return ResponseEntity.status(400).body(new ApiError("BALANCE_LOW", e.getMessage()));
  }
}
```

## 11. 재시도 & Fallback 로그
| 패턴 | 로깅 지침 |
|------|-----------|
| 재시도 | 시도 회차 + 원인 | `attempt=2 cause=timeout` |
| 서킷브레이커 Open | 최초 Open 시 단일 WARN | 반복 WARN 억제 |
| Fallback 실행 | INFO 레벨 | degrade 명시 |

## 12. 성능 & 비용 고려
| 문제 | 설명 | 완화 |
|------|------|------|
| 과도한 문자열 포맷 | 불필요 비용 | SLF4J placeholder |
| 대량 TRACE | 파일 I/O 증가 | 조건부 가드 `if(log.isDebugEnabled())` |
| 스택트레이스 폭발 | 반복 로깅 | 상위 계층 한 번만 |

## 13. 테스트 전략
| 유형 | 목적 | 기법 |
|------|------|------|
| 예외 발생 단위 테스트 | 규칙 위반 검증 | `assertThrows` |
| 로깅 검증 | 중요한 이벤트 기록 | Logback test appender |
| 회귀 테스트 | 잘못된 swallow 방지 | 예외 전파 assertions |

## 14. 흔한 실수
| 실수 | 결과 | 해결 |
|------|------|------|
| catch 후 무시 | 침묵 실패 | 최소 로그/재전파 |
| 광범위 Exception 캐치 | 원인 손실 | 구체 타입 | 
| 중복 로그 ERROR + 재throw | 로그 노이즈 | 한 번만 |
| 비밀 키 포함 로그 | 보안 위험 | 마스킹/제거 |

## 15. 체크리스트
- [ ] 비즈니스 규칙 예외 커스텀화
- [ ] 예외 메시지 컨텍스트 포함
- [ ] 중복 로깅 제거
- [ ] 민감정보 마스킹 정책 적용
- [ ] MDC로 핵심 상관키(correlation id) 추가

---
필요 시 Observability(Tracing, Metrics) 통합 문서 확장 가능.
