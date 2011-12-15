package com.googlecode.usc.svnfolder.compression;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ShunLi
 */
public class App {

    private final static List<String> YES_LIST = Arrays.asList("Y", "y", "true", "TRUE", "是");
    private final static String SPILT_CHAR = " | ";
    private final static String DEFAULT_EXCLUDED_WORDS = ".svn" + SPILT_CHAR + "target" + SPILT_CHAR + ".classpath" + SPILT_CHAR + ".project" + SPILT_CHAR + ".settings";

    // 1. svn folder patch
    // 2. type
    // 3. if use default exculde
    // 4. input exculde.

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请速来文件夹路径：");
        String folderPath = scanner.next();

        CompressionType[] compressionTypes = CompressionType.values();

        System.out.print("请选择压缩类型");

        for (int i = 0; i < compressionTypes.length; i++) {
            System.out.print("\n" + (i + 1) + "). " + compressionTypes[i]);
            if (i == 0) {
                System.out.print("(默认)");
            }
        }
        System.out.print("\n(选择数字)：");

        int choose = scanner.nextInt();
        if (choose < 1 || choose > compressionTypes.length) {
            choose = 0;
        } else {
            choose--;
        }

        CompressionType compressionType = compressionTypes[choose];

        System.out.print("是否按照默认的过滤规则(" + DEFAULT_EXCLUDED_WORDS + ")来压缩？(Y/N)：");

        boolean isUseDefault = isYse(scanner.next());

        String excludedWords = "";

        String isAgain = null;
        if (isUseDefault) {
            excludedWords = DEFAULT_EXCLUDED_WORDS;

            System.out.print("是否还添加过滤规则？(Y/N)：");
            isAgain = scanner.next();
        }

        if (!isUseDefault || isYse(isAgain)) {
            System.out.print("请添加过滤规则，多个过滤词以\"" + SPILT_CHAR + "\"分割：");
            scanner.nextLine();
            excludedWords = excludedWords + (!"".equals(excludedWords) ? SPILT_CHAR : "") + scanner.nextLine(); //
        }

        // System.out.println(excludedWords);

        List<String> excludedKeys = initExcludedKeys(excludedWords);

        File file = new File(folderPath);
        File[] files = file.listFiles();

        if (files != null) {
            File out = new File(file.getAbsolutePath() + compressionType.getSuffix());

            StrategyFactory sf = new StrategyFactory();
            sf.createStrategy(compressionType);

            Timer timer = new Timer();
            timer.schedule(new PrintTimerTask(), 0, 1000);

            System.out.println("压缩开始，请等待");
            new Context(sf.createStrategy(compressionType)).doCompress(files, out, excludedKeys);

            timer.cancel();
            System.out.println("\n压缩成功，请查看 " + out);
        } else {
            System.out.println("文件夹不存在或者输入的是文件!");
        }

    }

    // /////////////////////////////////////////////////////////////
    // util methods
    // /////////////////////////////////////////////////////////////
    private static boolean isYse(String next) {
        // StringUtils.isBlank(next) ||
        if (YES_LIST.contains(next)) {
            return true;
        }

        return false;
    }

    private static List<String> initExcludedKeys(String excludedWords) {
        List<String> excludedKeys = new ArrayList<String>();

        if (StringUtils.isNotBlank(excludedWords)) {
            excludedKeys.addAll(Arrays.asList(excludedWords.split("\\s" + SPILT_CHAR.trim() + "\\s")));
        }

        return excludedKeys;
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
