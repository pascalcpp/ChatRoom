package com.xpcf.chat.common;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author liuchengjie
 */
public class Utility {

    private static Scanner scanner = new Scanner(System.in);

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("res/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key返回配置文件的值
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return properties.getProperty(key);
    }
    /**
     * 从键盘读取特定长度的字符串
     * @param number
     * @return
     */
    public static String readString(int number) {
        String next = scanner.next();
        int length = next.length();
        if (length <= number) {
            String substring = next.substring(0, length);
            return substring;
        }
        String substring = next.substring(0, number);
        return substring;
    }
}
