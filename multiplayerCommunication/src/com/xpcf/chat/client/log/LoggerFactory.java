package com.xpcf.chat.client.log;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 10:52 PM
 */
public class LoggerFactory {

    private static Logger logger = new Logger();

    /**
     * TODO
     * @param clazz
     */
    public static Logger getLogger(Class<?> clazz) {
        return logger;
    }
}
