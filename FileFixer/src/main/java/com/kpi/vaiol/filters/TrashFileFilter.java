package com.kpi.vaiol.filters;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrashFileFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        Pattern p = Pattern.compile(".+\\.(bak|dmp)");
        Matcher m = p.matcher(name.toLowerCase());
        return m.matches();
    }
}
