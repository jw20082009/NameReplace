package com.wilbert;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件邮箱地址更换
 */
public class NameReplace {
    private static final String regex = "jiangwang\\.wilbert@bigo\\.sg";
    private static final String newStr = "";
    private static final String folder = "";

    public static void openFolder(File folder) {
        if (folder == null) {
            return;
        }
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                openFolder(file);
            } else {
                findStr(file, regex, "jw20082009@qq.com");
            }
        }
    }

    public static void findStr(File file, String regex, String newStr) {
        if (file == null || regex == null || newStr == null) {
            return;
        }
        BufferedReader bReader = null;
        boolean needReplace = false;
        try {
            FileReader reader = new FileReader(file);
            bReader = new BufferedReader(reader);
            String line = null;
            while ((line = bReader.readLine()) != null) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    needReplace = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (needReplace) {
            replaceStr(file, regex, newStr);
        }
    }

    public static void replaceStr(File file, String regex, String newStr) {
        if (file == null || regex == null || newStr == null) {
            return;
        }
        BufferedWriter writer = null;
        BufferedReader bufferedReader = null;
        File tempFile = new File(file.getAbsolutePath() + ".temp");
        try {
            FileWriter tempWriter = new FileWriter(tempFile);
            writer = new BufferedWriter(tempWriter);
            FileReader reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    line = line.replace(matcher.group(0), newStr);
                    System.out.println(file.getAbsolutePath() + "  " + matcher.group(0));
                }
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file.delete();
        tempFile.renameTo(file);
    }
}
