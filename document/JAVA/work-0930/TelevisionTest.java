import Television;

public class TelevisionTest {
    public static void main(String[] args) {
        Television tv = new Television(1,1,false);
        
        tv.channel = 7;
        tv.volume = 9;
        tv.onOff = true;

        System.out.println("The channel of TV is " + tv.channel + ", and the volume is" + tv.volume + ".");
    }
}