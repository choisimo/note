package ThirdOne.src.main.java;

import java.util.Random;
import java.util.Scanner;
// 다음의 조건을 만족하는 프로젝트를 개발하자.
// V (자유롭게) 부모클래스와 자식클래스를 생성
// V 부모클래스의 생성자를 3개 작성 (생성자 오버로딩 사용) 
// V 부모클래스의 메소드를 3개 작성
// V 자식클래스의 생성자를 3개 작성 (생성자 오버로딩 사용)
// V 자식클래스의 메소드를 3개 작성
// • 자식클래스의 생성자 코드안에 super() 키워드 넣기
// • 자식 클래스의 메소드 1개는 부모클래스 메소드 1개를 오버라이딩 함

public class thirdone {
    private static final Random RNG = new Random();

    public static void main(String[] args) {
        Marine marine = new Marine("Jim Raynor", 55, 8);
        Unit zerglingPack = new Unit("Zergling Pack", 60, 6);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        int round = 1;

        System.out.println("=== 스타크래프트 CUI 모의전 개시 ===");
        marine.move("전초기지");
        zerglingPack.move("크립 지대");

        while (running) {
            System.out.println("\n--- 라운드 " + round + " ---");
            marine.showStatus();
            zerglingPack.showStatus();

            if (!marine.isAlive() || !zerglingPack.isAlive()) {
                break;
            }

            System.out.println("명령을 선택하세요");
            System.out.println("1) 이동  2) 공격  3) 스팀팩  4) 엄폐  0) 철수");
            System.out.print("> ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("이동할 위치를 입력하세요: ");
                    String destination = scanner.nextLine().trim();
                    if (destination.isEmpty()) {
                        destination = "전열";
                    }
                    marine.move(destination);
                    break;
                case "2":
                    marine.attack(zerglingPack);
                    break;
                case "3":
                    marine.stimpack();
                    break;
                case "4":
                    marine.burrow();
                    break;
                case "0":
                    running = false;
                    System.out.println("마린이 철수합니다. 전투 종료.");
                    continue;
                default:
                    System.out.println("알 수 없는 명령입니다. 마린이 대기합니다.");
                    break;
            }

            if (!zerglingPack.isAlive()) {
                System.out.println("Zergling Pack이 섬멸되었습니다!");
                break;
            }

            enemyPhase(marine, zerglingPack);
            marine.endTurn();
            zerglingPack.endTurn();
            round++;
        }

        if (!marine.isAlive()) {
            System.out.println("마린이 쓰러졌습니다. 임무 실패.");
        } else if (!zerglingPack.isAlive()) {
            System.out.println("임무 완료! 지역이 확보되었습니다.");
        }

        scanner.close();
    }

    private static void enemyPhase(Marine marine, Unit enemy) {
        System.out.println("\n적이 행동합니다...");
        pause(350);

        if (enemy.hp < 15 && RNG.nextBoolean()) {
            enemy.move("후방" + (RNG.nextInt(3) + 1));
            System.out.println(enemy.name + " 이(가) 후퇴하여 재정비합니다.");
            enemy.heal(5);
            return;
        }

        if (RNG.nextInt(100) < 70) {
            enemy.attack(marine);
            int variance = RNG.nextInt(3); // 0~2 추가 데미지
            int rawDamage = enemy.attackPower + variance;
            int mitigated = marine.absorbDamage(rawDamage);
            marine.takeDamage(mitigated);
        } else {
            String flank = RNG.nextBoolean() ? "좌" : "우";
            enemy.move("측면 " + flank);
        }
    }

    private static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}