import main;

public class mutli {
    
    protected class car {
        protected car() {
            System.out.println("car");
        }
    }

    protected interface plane {
        // Override 사용 가능
        default protected void initMessage() {

        }   
        
    }

    private class flyingCar extends car implements plane {
        
    }



    private static void main(String[] args) {
        
    }
}