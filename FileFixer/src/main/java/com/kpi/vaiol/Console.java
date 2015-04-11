package com.kpi.vaiol;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Timer;


public class Console {

    private JTextArea textArea1;
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton STARTButton;
    private JCheckBox cleanFilesCheckBox;

    private Thread fixerThread;
    private Timer checkTimer;

    public Console() {
        if(new File("cid.txt").exists()) {
            java.util.List<String> lines = new ArrayList<String>();
            try {
                lines = Files.readAllLines(Paths.get("cid.txt"), Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
            setTextFields(lines.get(0), lines.get(1));
        } else {
            setTextFields("", "");
        }

        STARTButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fixerThread == null || fixerThread.getState() == Thread.State.TERMINATED) {
                    textArea1.setText("");
                    fixerThread = new Thread(new Fixer());
                    fixerThread.start();
                    STARTButton.setText("WAIT");
                    STARTButton.setEnabled(false);
                    checkTimer = new Timer(true);
                    checkTimer.scheduleAtFixedRate(new CheckTask(), 0, 50);
                }
            }
        });
    }

    public void out(String str) {
        textArea1.append(str + "\n");
        textArea1.setCaretPosition(textArea1.getDocument().getLength());
    }

    public void setTextFields(String num1, String num2) {

        textField2.setText(num1);
        textField1.setText(num2);
    }

    public boolean getClean() {
        return cleanFilesCheckBox.isSelected();
    }

    public String getTextField2() {
        return textField1.getText();
    }

    public String getTextField1() {
        return textField2.getText();
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public class CheckTask extends TimerTask {
        @Override
        public void run() {
            if (fixerThread != null && fixerThread.getState() != Thread.State.TERMINATED) {
                STARTButton.setText("WAIT");
                STARTButton.setEnabled(false);
            } else {
                STARTButton.setText("START");
                STARTButton.setEnabled(true);
                checkTimer.cancel();
            }
        }
    }
}
