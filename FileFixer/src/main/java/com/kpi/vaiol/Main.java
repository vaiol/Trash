package com.kpi.vaiol;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String FIXER = "fixer.exe";

    static int time = 0;

    public static void main(String[] args) {

        if(args[0] != null) {
            try {
                time = Integer.parseInt(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if(! new File("cid.txt").exists()) {
            System.out.println("ERROR: cid.txt does not exists!!!");
            return;
        }


        long dateBefore = System.currentTimeMillis();
        LoadFixer fixerLoader = new LoadFixer("fixer.exe");
        fixerLoader.load();
        getFix();
        long dateAfter = System.currentTimeMillis();

//        while ( ! fixerLoader.delete()) {
//            if (System.currentTimeMillis() - dateAfter > 3000) {
//                break;
//            }
//        }
        //cleanFiles();
        System.out.println();
        System.out.println("FixTime: " + ((dateAfter - dateBefore) / 1000.0) + " sec.");

    }

    public static void getFix() {

        String path = Paths.get("").toAbsolutePath().toString();

        File[] files1 = new File(path).listFiles(new NecessaryFileFilter());
        fixFiles(FIXER, files1);
    }

    private static void fixFiles(String fixer, File[] files) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        for (File file : files) {
            service.execute(new FixThread(fixer, file));
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        service.shutdown();
        while ( ! service.isTerminated()) {
        }
    }

    private static void cleanFiles() {

        String path = Paths.get("").toAbsolutePath().toString();

        File[] files1 = new File(path).listFiles(new BakFileFilter());

        for (File file : files1) {
            if(file.isFile()) {
                System.out.println("DEL: " + file.getName());
                file.delete();
            }
        }
    }

}
