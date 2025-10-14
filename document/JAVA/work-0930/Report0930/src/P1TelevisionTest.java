
public class P1TelevisionTest {
    public static void main(String[] args) {
        Television myTv = new Television();
        myTv.channel = 7;
        myTv.volume = 9;
        myTv.onOff = true;

        Television yourTv = new Television();
        yourTv.channel = 9;
        yourTv.volume = 12;
        yourTv.onOff = true;

        Television myTV = new Television();
        myTV.channel = 3;
        myTV.volume = 4;
        myTV.onOff = true;

        Television yourTV = new Television();
        yourTV.channel = 6;
        yourTV.volume = 1;
        yourTV.onOff = true;

        System.out.println("The channel of myTV is " + 
        myTv.channel + ", and the volume is " + myTv.volume + ".");
        System.out.println("The channel of yourTV is " + 
        yourTv.channel + ", and the volume is " + yourTv.volume + ".");

        System.out.println("The channel of myTV is " + 
        myTV.channel + ", and the volume is " + myTV.volume + ".");
        System.out.println("The channel of yourTV is " + 
        yourTV.channel + ", and the volume is " + yourTV.volume + ".");
    }
}

