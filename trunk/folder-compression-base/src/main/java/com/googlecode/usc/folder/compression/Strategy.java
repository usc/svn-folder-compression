package com.googlecode.usc.folder.compression;

import java.io.File;
import java.util.List;

/**
 *
 * @author ShunLi
 */
public interface Strategy {
    void doCompress(File[] files, File out, List<String> excludedKeys);
}
