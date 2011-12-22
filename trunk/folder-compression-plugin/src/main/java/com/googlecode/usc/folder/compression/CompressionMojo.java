package com.googlecode.usc.folder.compression;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

import com.googlecode.usc.folder.compression.CompressionType;
import com.googlecode.usc.folder.compression.Context;
import com.googlecode.usc.folder.compression.StrategyFactory;
import com.googlecode.usc.folder.compression.utils.CompressionUtil;

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
     * Excluded Keys.
     *
     * @parameter
     */
    private List<String> excludedKeys = new ArrayList<String>();

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

    public List<String> getExcludedKeys() {
        return excludedKeys;
    }

    public void setExcludedKeys(List<String> excludedKeys) {
        this.excludedKeys = excludedKeys;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    // @SuppressWarnings("unchecked")
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            FileUtils.forceMkdir(getGeneratedResourcesDirectory().getAbsoluteFile());
        } catch (IOException e) {
            throw new MojoFailureException(e.getMessage());
        }

        String destPath = FilenameUtils.concat(getGeneratedResourcesDirectory().getAbsolutePath(), getTargetPath());

        if (isVerbose()) {
            getLog().info("DestPath: " + destPath);
            getLog().info("CompressionType: " + getCompressionType());
            getLog().info("ExcludedWords: " + getExcludedKeys());
            getLog().info("Basedir: " + mavenProject.getBasedir());
        }

        CompressionType type = CompressionType.ZIP;
        if (getCompressionType() == null) {
            getLog().warn("Compression Type not provided, will process default compression type(ZIP)");
        } else {
            type = CompressionUtil.getEnumFromString(CompressionType.class, getCompressionType());
        }

        File file = mavenProject.getBasedir();
        File[] files = file.listFiles();

        if (files != null) {
            File out = new File(destPath, FilenameUtils.getBaseName(file.getAbsolutePath()) + type.getSuffix());

            StrategyFactory sf = new StrategyFactory();
            sf.createStrategy(type);

            getLog().info("Compression start, please wait");
            new Context(sf.createStrategy(type)).doCompress(files, out, getExcludedKeys());
            getLog().info("Compression success, please check " + out);
        }
    }
}
