package main;

public class Test {
    private static int x = -99999;
    
    public Test () {
        // static int : 
         x = Random.nextInt(100);
        System.out.println("Test class initialized with x = " + x);
    }

    public static void main(String[] args) {
        try {
            Test testInstance = new Test();
            MyRunnable myRunnable = (x) -> {
            run(x ? x: 10); };
            Thread thread = new Thread(myRunnable);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}

