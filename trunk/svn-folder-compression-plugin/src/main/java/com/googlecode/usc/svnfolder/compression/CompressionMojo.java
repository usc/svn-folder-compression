package com.googlecode.usc.svnfolder.compression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

/**
 * compression.
 *
 * @goal compression
 * @phase generate-resources
 * @requiresDependencyResolution runtime
 * @description compression.
 */
public class CompressionMojo extends AbstractCompressionMojo {

    /**
     * The current Maven project.
     *
     * @parameter default-value="${project}"
     * @readonly
     * @required
     */
    private MavenProject mavenProject;

    /**
     * Target path to put the resource
     *
     * @parameter default-value=""
     */
    private String targetPath;

    /**
     * compressionType. support ZIP, AR, CPIO, DUMP,TAR, JAR, BZIP2, GZIP, PACK200 type.
     *
     * @parameter
     */
    private String compressionType;
    /**
     * Excluded Words.
     *
     * @parameter
     */
    private List<String> excludedWords = new ArrayList<String>();

    /**
     * Verbose mode.
     *
     * @parameter expression="${compression.verbose}" default-value="false"
     */
    private boolean verbose;

    public String getTargetPath() {
        return (targetPath == null) ? "" : targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public List<String> getExcludedWords() {
        return excludedWords;
    }

    public void setExcludedWords(List<String> excludedWords) {
        this.excludedWords = excludedWords;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    @SuppressWarnings("unchecked")
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            FileUtils.forceMkdir(getGeneratedResourcesDirectory().getAbsoluteFile());
        } catch (IOException e) {
            throw new MojoFailureException(e.getMessage());
        }

        String destPath = FilenameUtils.concat(getGeneratedResourcesDirectory().getAbsolutePath(), getTargetPath());

        getLog().debug("destPath: " + destPath);
        getLog().debug("getCompressionType: " + getCompressionType());
        getLog().debug("getExcludedWords: " + getExcludedWords());
        getLog().debug("isVerbose: " + isVerbose());

        List<String> projectClasspath;
        try {
            projectClasspath = (List<String>) mavenProject.getCompileClasspathElements();
        } catch (DependencyResolutionRequiredException e) {
            getLog().error("Failed in gettting classpath elements", e);
            throw new MojoExecutionException("Failed in gettting classpath elements", e);
        }
        for (String path : projectClasspath) {
            getLog().debug("path: " + path);
        }

    }

}
