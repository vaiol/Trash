package com.kpi.vaiol;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;


public class Console {

    private JTextArea textArea1;
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton STARTButton;
    private JProgressBar progressBar1;
    private JCheckBox allFilesCheckBox;
    private JCheckBox cleanLogSCheckBox;
    private JCheckBox fixAllDirSCheckBox;
    private JCheckBox cleanFilesCheckBox;

    private Thread fixerThread;
    private Timer checkTimer;

    public Console() {
        STARTButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fixerThread == null || fixerThread.getState() == Thread.State.TERMINATED) {
                    textArea1.setText("");
                    fixerThread = new Thread(new MainFixerThread());
                    fixerThread.start();
                    STARTButton.setText("WAIT");
                    STARTButton.setEnabled(false);
                    allFilesCheckBox.setEnabled(false);
                    fixAllDirSCheckBox.setEnabled(false);
                    cleanFilesCheckBox.setEnabled(false);
                    cleanLogSCheckBox.setEnabled(false);
                    checkTimer = new Timer(true);
                    checkTimer.scheduleAtFixedRate(new CheckTask(), 0, 50);
                }
            }
        });
    }

    public void println(String str) {
        textArea1.append(str + "\n");
        textArea1.setCaretPosition(textArea1.getDocument().getLength());
        System.out.println(str);
    }

    public void setTextFields(String num1, String num2) {
        textField2.setText(num1);
        textField1.setText(num2);
    }

    public void setProgress(int value) {
        progressBar1.setValue(value);
    }

    public boolean isFixAllFiles() {
        return allFilesCheckBox.isSelected();
    }

    public boolean isCleanTrash() {
        return cleanFilesCheckBox.isSelected();
    }

    public boolean isCleanLogS() {
        return cleanLogSCheckBox.isSelected();
    }

    public boolean isFixAllDirS() {
        return fixAllDirSCheckBox.isSelected();
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
                allFilesCheckBox.setEnabled(false);
                fixAllDirSCheckBox.setEnabled(false);
                cleanFilesCheckBox.setEnabled(false);
                cleanLogSCheckBox.setEnabled(false);
            } else {
                STARTButton.setText("START");
                STARTButton.setEnabled(true);
                allFilesCheckBox.setEnabled(true);
                fixAllDirSCheckBox.setEnabled(true);
                cleanFilesCheckBox.setEnabled(true);
                cleanLogSCheckBox.setEnabled(true);
                checkTimer.cancel();
                setProgress(0);
            }
        }
    }
}
