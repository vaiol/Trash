package com.kpi.vaiol;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;

public class LoadFixer {
    private static File commands;
    private static File output;
    private static File errors;

    private File newFile;
    private String file;

    public LoadFixer(String file) {
        this.file = file;
    }

    public void load() {
        commands = new File("Commands.txt");
        output = new File("ProcessLog.txt");
        errors = new File("ErrorLog.txt");
        try {
            if(!commands.exists())
                commands.createNewFile();
            if(!output.exists())
                output.createNewFile();
            if(!errors.exists())
                errors.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResource(file).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


        newFile = new File(file);
        if(!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            copy(is, newFile);
        }
    }

    public boolean delete() {
        new File("cid.txt").delete();
        commands.delete();
        errors.delete();
        output.delete();
        return newFile.delete();
    }

    private static void copy(InputStream is, File dest) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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


    public static File getCommands() {
        return commands;
    }

    public static File getOutput() {
        return output;
    }

    public static File getErrors() {
        return errors;
    }
}
