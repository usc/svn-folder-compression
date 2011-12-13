package com.googlecode.usc.svnfolder.compression;

import java.io.File;
import java.util.Scanner;
import java.util.Timer;

import com.googlecode.usc.svnfolder.compression.utils.CompressionUtil;

/**
 *
 * @author ShunLi
 */
public class App {

    // 1. svn folder patch
    // 2. type
    // 3. if use default exculde
    // 4. input exculde.

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("pls input svn folder path: " + scanner.next());

        String svnFolder = "C:/Users/ShunLi/Desktop/svn-folder-compression";
        // String svnFolder = "D:/MSSE/";

        File svnFile = new File(svnFolder);
        File[] files = svnFile.listFiles();
        File out = new File(svnFile.getAbsolutePath() + ".zip");

        Timer timer = new Timer();
        timer.schedule(new PrintTimerTask(), 0, 1000);

        String type = "zip";
        CompressionType compressionType = CompressionUtil.getEnumFromString(CompressionType.class, type);

        StrategyFactory sf = new StrategyFactory();
        sf.createStrategy(compressionType);

        new Context(sf.createStrategy(compressionType)).doCompress(files, out);

        timer.cancel();
        System.out.println("\nZip => " + out + " completed.");
    }
}

class PrintTimerTask extends java.util.TimerTask {
    int count;

    @Override
    public void run() {
        System.out.print("·");
        if ((++count) % 60 == 0) { // 60s = 1min
            System.out.println();
        }
    }
}
