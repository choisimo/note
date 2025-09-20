# Java 학습/레퍼런스 문서 인덱스

이 디렉터리는 기본적인 Java 애플리케이션 설정(Manifest, JAR)부터 객체지향 설계, 디자인 패턴, 제네릭/컬렉션, 예외 처리, 동시성, 테스트 및 품질 관리까지 전반적인 실무 지향 레퍼런스를 제공합니다.

## 문서 목록
| 분류 | 문서 | 설명 |
|------|------|------|
| 패키징 & 실행 | [manifest_jar.md](manifest_jar.md) | Manifest.MF 구성, JAR 작성 & 실행, 모듈 시스템 개요 |
| 프로젝트 구조 | [project_structure.md](project_structure.md) | Maven/Gradle, 모듈 구조, 계층형 패키지 전략 |
| OOP 설계 | [oop_design.md](oop_design.md) | 클래스, 인터페이스, 추상화, 다형성, 설계 원칙 |
| 제네릭 & 컬렉션 & 스트림 | [generics_collections_streams.md](generics_collections_streams.md) | 제네릭 설계, 컬렉션 선택, 스트림 고급 |
| 예외 & 로깅 | [exception_logging.md](exception_logging.md) | 예외 전략, 로깅 구성(SLF4J/Logback) |
| 디자인 패턴 | [design_patterns.md](design_patterns.md) | SOLID, GoF 23패턴 요약 & 예시 |
| 동시성 & 성능 | [concurrency_performance.md](concurrency_performance.md) | 스레드/Executor/CompletableFuture/락/프로파일링 |
| 테스트 & CI | [testing_ci.md](testing_ci.md) | 테스트 피라미드, Mocking, CI 구성 |

## 사용 방법
1. 학습 순서 추천: Manifest/JAR → OOP 설계 → 제네릭/컬렉션 → 예외 & 로깅 → 디자인 패턴 → 동시성 → 테스트 & CI
2. 각 문서는 실무 예제 중심으로 핵심 개념 + 코드 스니펫을 제공합니다.
3. 추가로 다루고 싶은 주제가 있으면 저장소 이슈(또는 TODO 항목)로 남깁니다.

## 개선/TODO
- [x] 프로젝트 구조 문서 작성
- [x] OOP 설계 문서 작성
- [x] 제네릭 & 컬렉션 문서 작성
- [x] 예외 & 로깅 문서 작성
- [x] 디자인 패턴 문서 작성
- [x] 동시성 & 성능 문서 작성
- [x] 테스트 & CI 문서 작성

---
필요 시 Kotlin, Spring Boot 확장 문서도 추가 가능합니다. 요청 주세요.
