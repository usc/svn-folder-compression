package com.googlecode.usc.svnfolder.compression;

import java.io.File;
import org.apache.maven.plugin.AbstractMojo;

public abstract class AbstractCompressionMojo extends AbstractMojo {

    /**
     * Location for storing compression resource files.
     *
     * @parameter expression="${compression.generatedResourcesDirectory}"
     *            default-value="${project.build.directory}/compression"
     */
    private File generatedResourcesDirectory;

    public void setGeneratedResourcesDirectory(File generatedResourcesDirectory) {
        this.generatedResourcesDirectory = generatedResourcesDirectory;
    }

    protected File getGeneratedResourcesDirectory() {
        return generatedResourcesDirectory;
    }

}
