/**
 * Default method 
* [해결 방법] override 하면 괜찮음
------------------------------------------------------------------------------------------------------------------------------------------------
defaultMethod.java:37: error: conflict() in defaultMethod.boom cannot implement conflict() in Heaven
        void conflict();
             ^
  attempting to assign weaker access privileges; was public
defaultMethod.java:42: error: non-static variable this cannot be referenced from a static context
        boom b = new boom();
                 ^
2 errors
------------------------------------------------------------------------------------------------------------------------------------------------
 */


public class defaultMethod {
    
    public interface Hell {
        default void conflict() {
            System.out.println("From Hell");
        }

    }

    public interface Heaven {
        default void conflict() {
            System.out.println("From Heaven");
        }
    }

    public class boom implements Hell, Heaven {
        @Override
        void conflict();
            //System.out.println("?");        
    }

    public static void main(String[] args) {
        boom b = new boom();
        b.conflict(); // ????????????
    }
}