import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RandomWalk201911916 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int x = 6, y = 6;

        // 경로 추적용
        boolean[][] trace = new boolean[13][13];
        boolean[][] tile = new boolean[13][13];
        List<String> traceHistory = new ArrayList<>();

        int steps;              // 발자취 횟수 
        tile[y][x] = true;
        trace[y][x] = true;
        traceHistory.add(y + "," + x);

        // 벽 초기화 작업 
        int[][] bricks = new int[13][13];
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                bricks[i][j] = 0;
            }
        }

        // 외부 벽은 1로 설정
        for (int i = 0; i < 13; i++) {       
            bricks[0][i] = 1;
            bricks[12][i] = 1;
            bricks[i][0] = 1;
            bricks[i][12] = 1;
        }


        for (steps = 0; steps < 11; steps++) {
            int tempX = x; int tempY = y;
            int direction = (int) (Math.random() * 4);
            if (direction == 0 && x > 0)             // go left
            {
                x--;
            }
            else if (direction == 1 && x < 11)  // go right
            {
                x++;
            }
            else if (direction == 2 && y > 0)       // go up
            {
                y++;
            }
            else if (y < 11) {                                      // go down
                y--;
            }

            if (trace[y][x] == false && bricks[y][x] != 1) 
            {
                tile[y][x] = true;
                // 사람이 보면 뒤집혀 보임
                int y_view = 12 - y;
                traceHistory.add(x + "," +  y_view);
            } else {
                x = tempX;
                y = tempY;
                steps--;
                continue;
            }

            System.out.print("Enter .");
            input.nextLine();

            System.out.println("--------------------" + (steps + 1) + "--------------------");
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 13; j++) {
                        if (bricks[i][j] != 1 && tile[i][j] == true && trace[i][j] == false) {
                            System.out.print("N ");
                            trace[i][j] = true;
                        }
                        // traced position
                        else if (bricks[i][j] != 1 && tile[i][j] == true) {
                            System.out.print("# ");
                        } else if (bricks[i][j] != 1  && trace[i][j] == true) {
                            // no route 
                            System.out.println("No route");
                            return;
                        }
                        else  if (bricks[i][j] == 1) {
                            System.out.print("ㅁ");
                        }
                        else {
                            System.out.print(". ");
                        }
                    }
                    System.out.println("--------------------------------------------------"); 
            }
            System.out.println("-----------------------------------------------------");
            System.out.println("----trace history : " + traceHistory + "----------------------------------------");
            System.out.println("-----------------------------------------------------");
        }
    }
}