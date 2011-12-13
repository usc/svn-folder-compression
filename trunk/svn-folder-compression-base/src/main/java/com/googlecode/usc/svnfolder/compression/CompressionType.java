package com.googlecode.usc.svnfolder.compression;

/**
 *
 * @author ShunLi
 */
public enum CompressionType {
    ZIP(".zip"),
    TAR(".tar");
    //TODO-ShunLi: add more type

    private String suffix;

    private CompressionType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
