package com.xpcf.chat.client.context;

import java.io.IOException;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 10:49 PM
 */
public interface ClientChoice {

    /**
     * 策略模式
     */
    void execute() throws IOException;
}
