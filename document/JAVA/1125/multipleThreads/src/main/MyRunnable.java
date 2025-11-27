package main;

public class MyRunnable implements Runnable {
    @Override 
    public  void run(int x ) {
        for (int i = 0; i < x; i++) {
            System.out.println("MyRunnable running: " + i);
        }
    };
}