import java.awt.event.ActionListener;
import javax.swing.JLabel;

import java.awt.Frame;
import java.awt.event.ActionEvent;



public class MyListener implements ActionListener {

    protected class MyFrame extends Frame {
        private JLabel label;

    }

    @Override
    public void actionPerformed(ActionEvent act) {
        
    }

    private class MyListener implements ActionListener {
        public void actionPerformed(ActionEven ac) {
            if (e.getSource() == button) {
                label.setText("You just pressed the button!");
            }
        }
    }
}