package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CountDownFrame extends JFrame {
    private JPanel panel;
    private JLabel[] labels;

    public CountDownFrame() {
        setTitle("CountDown");
        setSize(300, 200);
    }
}