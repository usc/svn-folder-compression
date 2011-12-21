package com.googlecode.usc.svnfolder.compression;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Cleans out generated stale resources.
 *
 * @goal clean
 * @phase clean
 */
public class CompressionCleaningMojo extends AbstractCompressionMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            FileUtils.deleteDirectory(getGeneratedResourcesDirectory());
        } catch (IOException e) {
            throw new MojoFailureException(e.getMessage());
        }
    }

}
