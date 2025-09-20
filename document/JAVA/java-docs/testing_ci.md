# 테스트, 품질 & CI 가이드

## 1. 테스트 피라미드
| 계층 | 비율(권장) | 특징 | 도구 |
|------|-----------|------|------|
| 단위(Unit) | 60~70% | 빠름, 격리 | JUnit 5, Mockito |
| 서비스/통합 | 20~30% | DB/외부 연동 | Testcontainers, SpringBootTest |
| E2E(API/UI) | 5~10% | 시나리오 | REST Assured, Selenium |

## 2. JUnit 5 기본 패턴
```java
@DisplayName("Money add")
class MoneyTest {
  @Test
  void add(){
    Money a = Money.of(100, "KRW");
    Money b = Money.of(50, "KRW");
    assertEquals(150, a.add(b).amount());
  }

  @ParameterizedTest
  @CsvSource({"2,4,6", "3,5,8"})
  void sum(int a, int b, int expected){
    assertEquals(expected, a + b);
  }
}
```

## 3. Mockito / MockK (Java/Kotlin)
```java
when(repo.find("id1")).thenReturn(Optional.of(order));
verify(repo, times(1)).find("id1");
```
- 과도한 Mock → 구조 불안정 신호.

## 4. Testcontainers 예시
```java
static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:15");
@BeforeAll static void start(){ db.start(); }
```
실제 DB 환경 근사 (로컬 전용 인메모리 H2 차이 방지).

## 5. 통합 테스트 경량화 팁
| 문제 | 해결 |
|------|------|
| 느린 부팅 | 슬라이스 테스트 (@DataJpaTest) |
| Fixture 중복 | ObjectMother/Builder |
| Flaky 외부 호출 | WireMock/MockWebServer |

## 6. 코드 커버리지
- Line/Branch 모두 추적 (JaCoCo)
- 커버리지 수치는 목표가 아닌 신호 (품질 대체 불가)
- 핵심 도메인 규칙/에러 경로 필수 테스트

## 7. 정적 분석 & 품질
| 도구 | 목적 |
|------|------|
| SpotBugs | 바이트코드 결함 |
| Checkstyle | 코딩 규칙 |
| PMD | 코드 스멜 |
| ArchUnit | 아키텍처 의존 규칙 |
| SonarQube | 종합 품질 대시보드 |

## 8. CI 파이프라인 예 (GitHub Actions)
`.github/workflows/build.yml`
```yaml
name: build
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
          cache: gradle
      - name: Build
        run: ./gradlew build --scan --no-daemon
      - name: Test Report
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: reports
          path: build/reports/tests/test
```

## 9. 실패 빠른 피드백
| 전략 | 설명 |
|------|------|
| PR 단위 Build | 머지 전 품질 보증 |
| 단위 테스트 병렬 | Gradle `--parallel` |
| 변경 영향 테스트 | 변경된 모듈만 실행 |
| 캐시 활용 | Gradle Build Cache |

## 10. TDD 사이클 (Red-Green-Refactor)
| 단계 | 활동 | 산출 |
|------|------|------|
| Red | 실패 테스트 작성 | 명확 요구 |
| Green | 최소 구현 | 통과 코드 |
| Refactor | 중복 제거/개선 | 유지보수성 |

## 11. 성능/부하 테스트
| 유형 | 도구 | 목적 |
|------|------|-----|
| 마이크로 벤치 | JMH | 코드 단위 성능 |
| 부하/스트레스 | k6, Gatling | TPS/지연 한계 |
| 프로파일 | JFR, async-profiler | 병목 식별 |

## 12. 보안/품질 추가
| 항목 | 도구 |
|------|------|
| 의존 취약점 | `gradle dependencyCheck` / OWASP | 
| 라이선스 검사 | License Plugin | 
| 비밀 유출 | TruffleHog/Gitleaks |

## 13. 테스트 데이터 전략
| 전략 | 특징 | 주의 |
|------|------|----|
| Builder/Factory | 가독성 | 과도 복잡화 |
| Fixture 템플릿 | 재사용 | 전역 상태 오염 |
| 난수 데이터 | 경계 조건 탐색 | 재현성 Seed |

## 14. flaky 테스트 원인
| 원인 | 설명 | 해결 |
|------|------|------|
| 시간 의존 | 시스템 시간 | Clock 주입 |
| 쓰레드 레이스 | 비결정 순서 | 동기화, awaitility |
| 네트워크 호출 | 외부 상태 | Mock/Stub |
| 전역 상태 공유 | static 캐시 | 격리/리셋 |

## 15. 체크리스트
- [ ] 핵심 도메인 유스케이스 단위 테스트 존재
- [ ] 외부 시스템은 Testcontainers or Mock
- [ ] 품질 도구 CI 통합
- [ ] 커버리지 리포트 업로드
- [ ] flaky 테스트 모니터링 지표

---
요청 시 Maven 기반 설정 예시/품질 게이팅 추가 가능.
