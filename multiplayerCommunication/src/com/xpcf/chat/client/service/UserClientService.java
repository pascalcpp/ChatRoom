package com.xpcf.chat.client.service;

import java.io.IOException;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 10:51 PM
 */
public interface UserClientService {

    void checkUser(String userId, String password) throws IOException;
}
