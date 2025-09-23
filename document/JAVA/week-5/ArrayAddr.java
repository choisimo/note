import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Logger;


public class ArrayAddr  implements Serializable{
    
    public ArrayAddr() {
        notifyAll();
    }

    private static Logger log = Logger.getLogger("몰라");
    public static void main(String[] args) throws IOException {
        try {
            Scanner sc = new Scanner(System.in);
            File file1 = new File(sc.nextLine());
            
            if (!file1.exists()) {
                 throw new IOException("Not a valid file path");
            }
        } catch (Exception e) {
            log.warning("");
        } 
    }
}
