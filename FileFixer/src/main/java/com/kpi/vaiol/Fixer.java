package com.kpi.vaiol;


import com.kpi.vaiol.filters.BakFileFilter;
import com.kpi.vaiol.filters.NecessaryFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Fixer implements Runnable {

    private static final String FIXER = "fixer.exe";
    private static LoadFixer fixerLoader;

    public static void cidCreate() {
        if( ! new File("cid.txt").exists()) {
            try {
                new File("cid.txt").createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            String result = "";
            result += Main.getConsole().getTextField1() + "\n";
            result += Main.getConsole().getTextField1();
            try {
                Files.write(Paths.get("cid.txt"), result.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getFix() {

        String path = Paths.get("").toAbsolutePath().toString();
        File[] files = new File(path).listFiles(new NecessaryFileFilter());
        ExecutorService service = Executors.newFixedThreadPool(4);
        for (File file : files) {
            service.execute(new FixThread(FIXER, file));
        }
        service.shutdown();
        while ( ! service.isTerminated()) {
        }
    }

    private static void cleanFiles() {

        long date = System.currentTimeMillis();
        String path = Paths.get("").toAbsolutePath().toString();
        File[] files1 = new File(path).listFiles(new BakFileFilter());

        for (File file : files1) {
            if(file.isFile()) {
                Main.getConsole().out("DEL: " + file.getName());
                file.delete();
            }
        }

        while ( ! fixerLoader.delete()) {
            if (System.currentTimeMillis() - date > 3000) {
                break;
            }
        }
    }

    public void run() {
        long dateBefore = System.currentTimeMillis();
        cidCreate();
        fixerLoader = new LoadFixer("fixer.exe");
        fixerLoader.load();
        getFix();
        long dateAfter = System.currentTimeMillis();

        Main.getConsole().out("");
        if (Main.getConsole().getClean()) {
            cleanFiles();
        }
        Main.getConsole().out("FixTime: " + ((dateAfter - dateBefore) / 1000.0) + " sec.");

    }
}
