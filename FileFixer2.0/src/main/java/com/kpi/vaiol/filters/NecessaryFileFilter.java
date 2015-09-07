package com.kpi.vaiol.filters;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NecessaryFileFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        Pattern p = Pattern.compile(".+\\.(nv2|nv3|ap|psg|tdm|ck|d2t|pbp|tpg)");
        Matcher m = p.matcher(name.toLowerCase());
        return m.matches();
    }
}
