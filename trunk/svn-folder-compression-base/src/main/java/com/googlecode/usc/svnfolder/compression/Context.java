package com.googlecode.usc.svnfolder.compression;

import java.io.File;

/**
 *
 * @author ShunLi
 */
public class Context {
    private Strategy strategy = null;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void doCompress(File[] files, File out) {
        this.strategy.doCompress(files, out);
    }
}
