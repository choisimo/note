public class P3Television {
    int channel;
    int volume;
    boolean onOff;
    
    void print() {
        System.out.println("The channel is "
        + channel + ", and the volume is " + volume);
    }

    int getChannel() {
        return channel;
    }

    String  IsOnOff() {
        return this.onOff == false ? "Tv is On" : "Tv is Off";
    }

    public static void main(String[] args) {
        P3Television tv = new P3Television();
        System.err.println(tv.IsOnOff());
    }
}
