package com.kpi.vaiol;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static JFrame frame;
    private static Console console = new Console();

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension((screenSize.width / 2), (screenSize.height / 2));
        int x = frameSize.width / 2;
        int y = frameSize.height / 2;

        frame = new JFrame("Console");
        frame.setContentPane(console.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(x, y, frameSize.width, frameSize.height);
        frame.setMinimumSize(new Dimension(700, 420));
        frame.setVisible(true);

    }

    public static Console getConsole() {
        return console;
    }
}
