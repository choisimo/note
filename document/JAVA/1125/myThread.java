import java.util.Random;

import Random.*;

/**
 *  thread implements Runnable 
 *  thread extends Thread  
*   -> difference : thread 상속 vs thread 포함
*   -> 기본 class의 다중 상속 불가 때문에 implements Runnable 을 더 권장함
*/
public class myThread extends Thread {
    public static void main(String[] args) {
        myThread th1 = new myThread();
        Random rand = new Random();
        th1.start(new Runnable() {
            @Override
            public void run() {
                
            }
    }
}  