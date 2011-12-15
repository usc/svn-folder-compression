package com.googlecode.usc.svnfolder.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FilenameUtils;

import com.googlecode.usc.svnfolder.compression.utils.CompressionUtil;

/**
 *
 * @author ShunLi
 */
public class ZipStrategy implements Strategy {
    private final static int BUFFER_SIZE = 128;

    public void doCompress(File[] files, File out, List<String> excludedKeys) {
        Map<String, File> map = new HashMap<String, File>();
        String parent = FilenameUtils.getBaseName(out.getName());

        for (File f : files) {
            CompressionUtil.list(f, parent, map, excludedKeys);
        }

        if (!map.isEmpty()) {
            ZipArchiveOutputStream zos = null;
            try {
                zos = new ZipArchiveOutputStream(out);

                for (Map.Entry<String, File> entry : map.entrySet()) {
                    File file = entry.getValue();

                    ZipArchiveEntry ze = new ZipArchiveEntry(file, entry.getKey());
                    zos.putArchiveEntry(ze);

                    if (file.isFile()) {
                        InputStream is = new FileInputStream(file);
                        byte[] b = new byte[BUFFER_SIZE];
                        int i = -1;
                        while ((i = is.read(b)) != -1) {
                            zos.write(b, 0, i);
                        }
                        is.close();
                    }

                    zos.closeArchiveEntry();
                }

                zos.finish();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (zos != null) {
                    try {
                        zos.close();
                    } catch (IOException e) { /* swallow */
                    }
                }
            }
        }
    }
}
