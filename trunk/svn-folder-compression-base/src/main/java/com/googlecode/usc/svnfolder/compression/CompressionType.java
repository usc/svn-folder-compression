package com.googlecode.usc.svnfolder.compression;

/**
 *
 * @author ShunLi
 *
 *         http://commons.apache.org/compress/examples.html
 */
public enum CompressionType {
    ZIP(".zip"),
    AR(".ar"),
    CPIO(".crio"),
    DUMP(".dump"),
    TAR(".tar"),
    JAR(".jar"),
    BZIP2(".bzip2"),
    GZIP(".gzip"),
    PACK200(".pak200");

    private String suffix;

    private CompressionType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
