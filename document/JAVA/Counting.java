public class Counting {
    public static void main (String[] args) 
    {
        String s = "no news is good news";
        int number = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 's') {
                number++;
                continue;
            } 
        }
        System.out.println("Counting number of n: " + number);
    }
}