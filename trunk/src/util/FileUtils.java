package util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class FileUtils {

    public static ArrayList<File> getFileListFlat(File source, String[] ignoreList) {
        ArrayList<File> ret = new ArrayList<File>();

        boolean doListForMe = true;
        for (int i = 0; i < ignoreList.length; i++) {
            if (ignoreList[i].equalsIgnoreCase(source.getName())) {
                return ret;
            }
        }


        if (source.isDirectory()) {
            File[] files = source.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    ret.addAll(getFileListFlat(files[i], ignoreList));
                } else {
                    ret.add(files[i]);
                }
            }
            return ret;
        } else {
            ret.add(source);
            return ret;
        }

    }

    public static ArrayList<File> getFileListFlat(File source) {
        ArrayList<File> ret = new ArrayList<File>();

        if (source.isDirectory()) {
            File[] files = source.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    ret.addAll(getFileListFlat(files[i]));
                } else {
                    ret.add(files[i]);
                }
            }
            return ret;
        } else {
            ret.add(source);
            return ret;
        }

    }

    public static ArrayList<File> getFileListFlat(File source, final String filter) {
        ArrayList<File> ret = new ArrayList<File>();

        if (source.isDirectory()) {
            File[] files = source.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return (pathname.getName().endsWith(filter)
                            || pathname.getName().endsWith(filter.toLowerCase())
                            || pathname.getName().endsWith(filter.toUpperCase()) ? true : false);
                }
            });
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    ret.addAll(getFileListFlat(files[i]));
                } else {
                    ret.add(files[i]);
                }
            }
            return ret;
        } else {
            ret.add(source);
            return ret;
        }

    }
}
