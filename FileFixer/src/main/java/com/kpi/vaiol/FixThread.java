package com.kpi.vaiol;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FixThread implements Runnable {

    String fixer;
    File file;

    public FixThread(String fixer, File file) {
        this.fixer = fixer;
        this.file = file;
    }

    @Override
    public void run() {
        ProcessBuilder processBuilder = new ProcessBuilder(fixer, file.getName());

        processBuilder.redirectErrorStream(true);
        File commands = LoadFixer.getCommands();
        File output = LoadFixer.getOutput();
        File errors = LoadFixer.getErrors();

        processBuilder.redirectInput(commands);
        processBuilder.redirectError(errors);
        processBuilder.redirectOutput(output);
        try {
            Process p = processBuilder.start();
            assert p.getInputStream().read() == -1;
            p.waitFor();
            System.out.println("FIX: "+file.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: " + file.getName());
        }
        assert processBuilder.redirectInput() == ProcessBuilder.Redirect.PIPE;
    }
}
