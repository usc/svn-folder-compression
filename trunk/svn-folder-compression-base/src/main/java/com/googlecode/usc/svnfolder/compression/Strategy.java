package com.googlecode.usc.svnfolder.compression;

import java.io.File;

/**
 *
 * @author ShunLi
 */
public interface Strategy {
    void doCompress(File[] files, File out);
}
