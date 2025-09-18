/**
 * 연습 문제 3
 * 파일 이름 : InfiniteLoop.java
 */
public class InfiniteLoop {
    public static void main(String[] args) 
    {
        String s = "Please give me the grade A!!!!!!!";
        
        boolean lock = false;

        Long st = System.nanoTime();
        System.out.println(st);

        while (true) {                        
            
        if ((st % 2 == 0)){
            break;
        }

        System.out.println(s);
            continue;
        }
    }     
}
