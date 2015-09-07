package com.kpi.vaiol;


import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileFixThread implements Runnable {

    private File file;
    private int percent;

    public FileFixThread(File file, int percent) {
        this.file = file;
        this.percent = percent;
    }

    public boolean is3D(String name) {
        Pattern p = Pattern.compile(".+\\.(pbp|tpg)");
        Matcher m = p.matcher(name.toLowerCase());
        return m.matches();
    }

    public void run() {
        String path = file.getParentFile().getAbsolutePath() + "\\";
        File commands = new File(path + "comFixJar.txt");
        File output = new File(path + "outFixJar.txt");
        File errors = new File(path + "errFixJar.txt");
        String fixerName;
        if (is3D(file.getName())) {
            fixerName = "fixer3d.exe";
        } else {
            fixerName = "fixer.exe";
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectInput(commands);
        processBuilder.redirectError(errors);
        processBuilder.redirectOutput(output);
        processBuilder.directory(new File(path));
        processBuilder.command("java", "-jar", path + "fix.jar", fixerName, file.getName());

        try {
            Process p = processBuilder.start();
            assert p.getInputStream().read() == -1;
            p.waitFor();
            Main.getConsole().println("FIX: " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
            Main.getConsole().println("ERROR: " + file.getName() + e);
        }
        assert processBuilder.redirectInput() == ProcessBuilder.Redirect.PIPE;
        Main.getConsole().setProgress(percent);
    }
}
