public class Television {
        Television (int ch, int vol, boolean pwr) {
            this.channel = ch;
            this.volume = vol;
            this.onOff = pwr;
        }

        protected int channel;
        protected int volume;
        protected boolean onOff;
}