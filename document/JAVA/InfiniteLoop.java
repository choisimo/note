/**
 * 연습 문제 3
 * 파일 이름 : InfiniteLoop.java
 */
public class InfiniteLoop {
    public static void main(String[] args) 
    {
        String s = "Please give me the grade A!!!!!!!";
        
        boolean lock = false;
        while (!lock)
            {
            while (true) {                        
            
            Long st = System.nanoTime();
            System.out.println(s);
            
            if ((st % 2 == 0)){
                lock = true;
                break;
            } else {
                lock = false;
                    continue;
                }
            }
        }
    }     
}
