package com.kpi.vaiol;


import com.kpi.vaiol.filters.TrashFileFilter;
import com.kpi.vaiol.filters.NecessaryFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFixerThread implements Runnable {

    private static final double ONE_SECOND_MILLIS = 1000.0;
    private static final int START_FIRST_PHASE = 0;
    private static final int START_SECOND_PHASE = 5;
    private static final int START_THIRD_PHASE = 80;
    private static final int START_FOURTH_PHASE = 95;
    private static final int END_ALL_PHASES = 100;
    private static final int MAX_WAIT_TIME = 6000;
    private static final int THREAD_COUNT = 4;
    private static final String CID_TXT = "cid.txt";

    private List<File> files;
    private List<Fixer> fixers;
    private List<String> paths;
    private boolean allFiles;
    private boolean allDirs;
    private boolean cleanLogs;
    private boolean cleanTrash;

    private void cidCreate() {
        File cid = new File(CID_TXT);
        if( ! cid.exists()) {
            try {
                cid.createNewFile();
                Main.getConsole().println(CID_TXT + " was created");
            } catch (IOException e) {
                e.printStackTrace();
                Main.getConsole().println(e.toString());
            }
            String cidString = "";
            cidString += Main.getConsole().getTextField1() + "\n";
            cidString += Main.getConsole().getTextField2();
            try {
                Files.write(Paths.get(CID_TXT), cidString.getBytes());
                Main.getConsole().println(CID_TXT + " was wrote");
            } catch (IOException e) {
                e.printStackTrace();
                Main.getConsole().println(e.toString());
            }
            Main.getConsole().println("");
        }
    }

    private void cleanCid() {
        File cid = new File(CID_TXT);
        if(cid.exists()) {
            cid.delete();
            Main.getConsole().println(CID_TXT + " was deleted");
        }
    }

    private void findFiles(File file) {
        File[] necessaryFiles;
        if (allFiles) {
            necessaryFiles = file.listFiles();
        } else {
            necessaryFiles = file.listFiles(new NecessaryFileFilter());
        }
        if (necessaryFiles != null && necessaryFiles.length > 0) {
            files.addAll(Arrays.asList(necessaryFiles));
        }
        if (!allDirs) {
            return;
        }
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    findFiles(f);
                }
            }
        }
    }


    private void getFix() {
        if (files.isEmpty()) {
            return;
        }
        ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < files.size(); i++) {
            if(files.get(i).isFile()) {
                int percent = getPercentage(i, files.size(), 5, 80);
                service.execute(new FileFixThread(files.get(i), percent));
            }
        }
        service.shutdown();
        while ( ! service.isTerminated()) {
        }
        Main.getConsole().println("");
    }

    private void loadFixerFiles() {
        if (files.isEmpty()) {
            return;
        }
        Set<String> filePaths = new LinkedHashSet<String>();
        for (File file : files) {
            filePaths.add(file.getParentFile().getAbsolutePath());
        }
        paths = new ArrayList<String>(filePaths);
        for (int i = 0; i < paths.size(); i++) {
            Main.getConsole().setProgress(getPercentage(i, paths.size(), START_FIRST_PHASE, START_SECOND_PHASE));
            Fixer fixer = new Fixer(paths.get(i));
            fixer.load();
            fixers.add(fixer);
        }

    }

    private int getPercentage(int current, int arraySize, int minimum, int maximum) {
        double p = ((double) (current + 1) / arraySize);
        return minimum + (int) (p * maximum);
    }


    private void cleanTrashFolder(String path) {
        File[] files = new File(path).listFiles(new TrashFileFilter());
        for (int i = 0; i < files.length; i++) {
            if(files[i].isFile()) {
                Main.getConsole().println("DELETE: " + files[i].getName());
                files[i].delete();
            }
        }
        Main.getConsole().println("");
    }

    private void cleanTrashFiles() {
        if (paths == null) {
            return;
        }
        for (int i = 0; i < paths.size(); i++) {
            cleanTrashFolder(paths.get(i));
            Main.getConsole().setProgress(getPercentage(i, paths.size(), START_THIRD_PHASE, START_FOURTH_PHASE));
        }
    }

    private void cleanFiles() {
        long date;
        int startPercent = START_THIRD_PHASE;
        if (cleanTrash) {
            cleanTrashFiles();
            cleanCid();
            startPercent = START_FOURTH_PHASE;
        }

        for (int i = 0; i < fixers.size(); i++) {
            date = System.currentTimeMillis();

            while (!fixers.get(i).deleteExecs()) {
                if (System.currentTimeMillis() - date > MAX_WAIT_TIME) {
                    System.out.println("can't clean exec's");
                    break;
                }
            }

            if(cleanLogs) {
                date = System.currentTimeMillis();
                while (!fixers.get(i).deleteLogs()) {
                    if (System.currentTimeMillis() - date > MAX_WAIT_TIME) {
                        System.out.println("can't clean logs");
                        break;
                    }
                }
            }
            Main.getConsole().setProgress(getPercentage(i, paths.size(), startPercent, END_ALL_PHASES));
        }
    }

    public void run() {
        allFiles = Main.getConsole().isFixAllFiles();
        allDirs = Main.getConsole().isFixAllDirS();
        cleanLogs = Main.getConsole().isCleanLogS();
        cleanTrash = Main.getConsole().isCleanTrash();
        files = new ArrayList<File>();
        fixers = new ArrayList<Fixer>();
        String path = Paths.get("").toAbsolutePath().toString();
        File currentDir = new File(path);

        long dateBefore = System.currentTimeMillis();
        Main.getConsole().println("Current directory: " + currentDir.getAbsolutePath());
        findFiles(currentDir);
        cidCreate();
        loadFixerFiles();
        getFix();
        cleanFiles();
        long dateAfter = System.currentTimeMillis();

        Main.getConsole().println("FixTime: " + ((dateAfter - dateBefore) / ONE_SECOND_MILLIS) + " sec.");

    }
}
