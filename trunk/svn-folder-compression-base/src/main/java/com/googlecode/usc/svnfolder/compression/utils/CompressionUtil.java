package com.googlecode.usc.svnfolder.compression.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lan
 */
public final class CompressionUtil {
    public static void list(File f, String parent, Map<String, File> map, List<String> excludedKeys) {
        String name = f.getName();

        if (parent != null) {
            name = parent + "/" + name;// set zip parent location.
        }
        // is exclude?
        if (!isExcluded(f, excludedKeys)) {
            if (f.isFile()) {
                map.put(name, f);
            } else if (f.isDirectory()) {
                File[] subFiles = f.listFiles();

                if (subFiles.length > 0) {
                    for (File file : subFiles) {
                        list(file, name, map, excludedKeys);
                    }
                } else { // empty directory
                    map.put(name, f);
                }
            }
        }
    }

    /**
     * is exclude?
     *
     * @param f
     * @return
     */
    protected static boolean isExcluded(File f, List<String> excludedKeys) {
        for (String key : excludedKeys) {
            if (key.equalsIgnoreCase(f.getName())) {
                return true;
            }

        }
        return false;
    }
}
