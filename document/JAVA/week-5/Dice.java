public class Dice {
    public static void main(String[] args) {
        int rand;
        for (int i = 0; i < 10; i ++) {
            // int -> type  casting, 0~5 --> 6, +1 --> 1 만큼 shift 연산
             rand = (int) (Math.random() * 6) + 1; 
             System.out.println(rand);
        }
    }
}