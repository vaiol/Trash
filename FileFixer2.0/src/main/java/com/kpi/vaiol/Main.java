package com.kpi.vaiol;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private final static String CID = "cid.txt";
    private static Console console;

    public static void main(String[] args) {
        console = new Console();
        readCid();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension((screenSize.width / 2), (screenSize.height / 2));
        int x = frameSize.width / 2;
        int y = frameSize.height / 2;

        JFrame frame;
        frame = new JFrame("Console");
        frame.setContentPane(console.getPanel1());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(x, y, frameSize.width, frameSize.height);
        frame.setMinimumSize(new Dimension(700, 420));
        frame.setVisible(true);
    }

    private static void readCid() {
        if (new File(CID).exists()) {
            java.util.List<String> lines = new ArrayList<String>();
            try {
                lines = Files.readAllLines(Paths.get(CID), Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (! (lines.size() < 2)) {
                console.setTextFields(lines.get(0), lines.get(1));
            } else {
                Main.getConsole().println(CID + " is incorrect!");
                console.setTextFields("", "");
            }
        } else {
            Main.getConsole().println(CID + " not found");
            console.setTextFields("", "");
        }
    }

    public static Console getConsole() {
        return console;
    }
}
