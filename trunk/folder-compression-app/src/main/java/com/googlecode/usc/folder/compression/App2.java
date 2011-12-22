package com.googlecode.usc.folder.compression;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.googlecode.usc.folder.compression.CompressionType;
import com.googlecode.usc.folder.compression.Context;
import com.googlecode.usc.folder.compression.StrategyFactory;
import com.googlecode.usc.folder.compression.utils.CompressionUtil;
import com.googlecode.usc.folder.compression.utils.PrintTimerTask;

public class App2 {

    private static final String FOLDER_PATH = "folderPath";
    private static final String COMPRESSION_TYPE = "compressionType";
    private static final String EXCLUDED_WORDS = "excludedWords";

    public static void main(String[] args) {
        String folderPath = null;
        String compressionType = null;
        String excludedWords = null;

        // process command line
        Options options = new Options();

        options.addOption(FOLDER_PATH, true, "Folder Path");
        CompressionType[] compressionTypes = CompressionType.values();
        options.addOption(COMPRESSION_TYPE, true, "Compression Type, support " + Arrays.toString(compressionTypes));
        options.addOption(EXCLUDED_WORDS, true, "Excluded Words, multiple filter words separated with a backslash");
        options.addOption("help", false, "Print this help message");

        CommandLineParser parser = new GnuParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Failed to parse argument: " + e.getMessage());
            printUsage(options);
            System.exit(1);
        }

        if (cmd.hasOption("help")) {
            printUsage(options);
            // System.exit(2);
        }

        if (cmd.hasOption(FOLDER_PATH)) {
            folderPath = cmd.getOptionValue(FOLDER_PATH);
        }
        if (cmd.hasOption(COMPRESSION_TYPE)) {
            compressionType = cmd.getOptionValue(COMPRESSION_TYPE);
        }
        if (cmd.hasOption(EXCLUDED_WORDS)) {
            excludedWords = cmd.getOptionValue(EXCLUDED_WORDS);
        }

        if (folderPath == null) {
            System.err.println("Folder Path not provided, please define in command line argument");
            System.exit(1);
        }

        CompressionType type = CompressionType.ZIP;
        if (compressionType == null) {
            System.out.println("Compression Type not provided, will process default compression type(ZIP)");
        } else {
            type = CompressionUtil.getEnumFromString(CompressionType.class, compressionType);
        }

        List<String> excludedKeys = CompressionUtil.initExcludedKeys(excludedWords);

        File file = new File(folderPath);
        File[] files = file.listFiles();

        if (files != null) {
            File out = new File(file.getAbsolutePath() + type.getSuffix());

            StrategyFactory sf = new StrategyFactory();
            sf.createStrategy(type);

            Timer timer = new Timer();
            timer.schedule(new PrintTimerTask(), 0, 1000);

            System.out.println("\nCompression start, please wait.");
            new Context(sf.createStrategy(type)).doCompress(files, out, excludedKeys);

            timer.cancel();
            System.out.println("\nCompression success, please check " + out + "\n");
        } else {
            System.err.println("folder is not exist or it's a file!");
            System.exit(1);
        }

    }

    private static void printUsage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Define in command line argument", options);
    }

}
