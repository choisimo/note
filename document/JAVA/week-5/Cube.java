// Cube.java

import java.util.Arrays;

// 색상을 나타내는 열거형
enum Color {
    W, // White (윗면)
    Y, // Yellow (아랫면)
    B, // Blue (앞면)
    G, // Green (뒷면)
    R, // Red (왼쪽면)
    O; // Orange (오른쪽면)
}

public class Cube {
    // 0:Up(W), 1:Down(Y), 2:Front(B), 3:Back(G), 4:Left(R), 5:Right(O)
    private Color[][] up = new Color[3][3];
    private Color[][] down = new Color[3][3];
    private Color[][] front = new Color[3][3];
    private Color[][] back = new Color[3][3];
    private Color[][] left = new Color[3][3];
    private Color[][] right = new Color[3][3];
    
    // 간편한 접근을 위해 모든 면을 배열로 묶음
    private Color[][][] faces = {up, down, front, back, left, right};

    public Cube() {
        // 초기 상태 설정: 각 면을 해당 색상으로 채움
        fillFace(up, Color.W);
        fillFace(down, Color.Y);
        fillFace(front, Color.B);
        fillFace(back, Color.G);
        fillFace(left, Color.R);
        fillFace(right, Color.O);
    }

    private void fillFace(Color[][] face, Color color) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                face[i][j] = color;
            }
        }
    }

    // 큐브 상태를 콘솔에 출력하는 메서드
    public void printCube() {
        System.out.println("====== Cube State ======");
        // 윗면 출력
        printFace(up, "      ");
        // 중간 4개 면 (왼쪽, 앞, 오른쪽, 뒤) 가로 출력
        for (int i = 0; i < 3; i++) {
            printRow(left, i);
            System.out.print(" ");
            printRow(front, i);
            System.out.print(" ");
            printRow(right, i);
            System.out.print(" ");
            printRow(back, i);
            System.out.println();
        }
        // 아랫면 출력
        printFace(down, "      ");
        System.out.println("=======================");
    }
    
    private void printFace(Color[][] face, String prefix) {
        for (int i = 0; i < 3; i++) {
            System.out.print(prefix);
            printRow(face, i);
            System.out.println();
        }
    }
    
    private void printRow(Color[][] face, int row) {
        for (int j = 0; j < 3; j++) {
            System.out.print(face[row][j] + " ");
        }
    }

    // 여기에 큐브 회전 메서드가 추가됩니다.
}