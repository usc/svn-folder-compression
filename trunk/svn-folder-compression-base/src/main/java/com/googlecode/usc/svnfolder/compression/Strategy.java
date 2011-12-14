package com.googlecode.usc.svnfolder.compression;

import java.io.File;
import java.util.List;

/**
 *
 * @author ShunLi
 */
public interface Strategy {
    void doCompress(File[] files, File out, List<String> excludedKeys);
}
