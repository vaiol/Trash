package com.kpi.vaiol;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Fixer {

    private final static String FIX_JAR = "fix.jar";
    private final static String FIXER = "fixer.exe";
    private final static String FIXER_3D = "fixer3d.exe";
    private final static String CID = "cid.txt";
    private final static String SLASH = "\\";
    private final static int BUFFER_SIZE = 1024;


    private String path;
    private File outFixer;
    private File errFixer;
    private File comFixer;
    private File outFixer3D;
    private File errFixer3D;
    private File comFixer3D;
    private File outFixJar;
    private File errFixJar;
    private File comFixJar;
    private File fixer;
    private File fixJar;
    private File fixer3D;

    public Fixer(String path) {
        this.path = path + SLASH;
    }

    private File createFile(String name) {
        File nFile = new File(path + name);
        if (!nFile.exists()) {
            try {
                nFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nFile;
    }

    private File createFixerFile(String name) {
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResource(name).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException nex) {
            nex.printStackTrace();
        }

        File fixer = new File(path + name);
        if (!fixer.exists()) {
            try {
                fixer.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            copy(is, fixer);
        }
        return fixer;
    }

    public void load() {
        outFixer = createFile("OutFixer.txt");
        errFixer = createFile("ErrFixer.txt");
        comFixer = createFile("ComFixer.txt");
        outFixer3D = createFile("OutFixer3D.txt");
        errFixer3D = createFile("ErrFixer3D.txt");
        comFixer3D = createFile("ComFixer3D.txt");
        outFixJar = createFile("OutFixJar.txt");
        errFixJar = createFile("ErrFixJar.txt");
        comFixJar = createFile("ComFixJar.txt");
        fixer = createFixerFile(FIXER);
        fixer3D = createFixerFile(FIXER_3D);
        fixJar = createFixerFile(FIX_JAR);



        try {
            Files.copy(new File(CID).toPath(), new File(path + CID).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteLogs() {
        boolean isDeleted;
        isDeleted = outFixer.delete();
        isDeleted = isDeleted && errFixer.delete();
        isDeleted = isDeleted && comFixer.delete();
        isDeleted = isDeleted && outFixer3D.delete();
        isDeleted = isDeleted && errFixer3D.delete();
        isDeleted = isDeleted && comFixer3D.delete();
        isDeleted = isDeleted && outFixJar.delete();
        isDeleted = isDeleted && errFixJar.delete();
        isDeleted = isDeleted && comFixJar.delete();
        return isDeleted;
    }

    public boolean deleteExecs(){
        boolean isDeleted;
        isDeleted = new File(path + CID).delete();
        isDeleted = isDeleted && fixJar.delete();
        isDeleted = isDeleted && fixer.delete();
        isDeleted = isDeleted && fixer3D.delete();
        try {
            Files.delete(Paths.get(fixJar.getAbsolutePath()));
            Files.delete(Paths.get(fixer.getAbsolutePath()));
            Files.delete(Paths.get(fixer3D.getAbsolutePath()));
        } catch (IOException e) {
        }
        return isDeleted;
    }

    private static void copy(InputStream is, File dest) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
