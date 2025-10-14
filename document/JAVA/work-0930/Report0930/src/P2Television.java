public class P2Television {
    int channel;
    int volume;
    boolean onOff;

    void print() {
        System.out.println("The channel is " + 
        channel + ", and the volume is " + volume);
    }

    public int getVolume() {
        return this.volume;
    }

    public static void main(String[] args) {
        P2Television tv = new P2Television();
        tv.volume = 1;
        System.out.println("Current Volume is " + tv.getVolume());
    }
    
}
