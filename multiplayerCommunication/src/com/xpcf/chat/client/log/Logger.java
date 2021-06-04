package com.xpcf.chat.client.log;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 10:52 PM
 */
public class Logger {

//    TODO
    private Class<?> clazz;

    public void print(String text) {
        System.out.println(text);
    }

    public void info(String text) {
        print(text);
    }

}
