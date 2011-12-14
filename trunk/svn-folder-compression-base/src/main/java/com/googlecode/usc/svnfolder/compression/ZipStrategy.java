package com.googlecode.usc.svnfolder.compression;

import java.io.File;
import java.util.List;

import com.googlecode.usc.svnfolder.compression.utils.CompressionUtil;

/**
 *
 * @author ShunLi
 */
public class ZipStrategy implements Strategy {

    @Override
    public void doCompress(File[] files, File out, List<String> excludedKeys) {
        CompressionUtil.zip(files, out);// TODO-ShunLi: temp op.
    }

    // TODO-ShunLi: find a issus
    // can't zip empty folders.
}
