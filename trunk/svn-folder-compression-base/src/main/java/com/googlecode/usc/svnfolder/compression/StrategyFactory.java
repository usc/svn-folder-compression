package com.googlecode.usc.svnfolder.compression;


/**
 *
 * @author ShunLi
 */
public class StrategyFactory {
    public Strategy createStrategy(CompressionType compressionType) {
        Strategy strategy = new ZipStrategy();// default strategy

        if (CompressionType.ZIP == compressionType) {
            // no-op = default
        } else {
            // TODO-ShunLi: later
        }

        return strategy;
    }
}
