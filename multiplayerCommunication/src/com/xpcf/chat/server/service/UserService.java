package com.xpcf.chat.server.service;

import com.xpcf.chat.common.MessageEnum;
import com.xpcf.chat.common.entity.User;
import com.xpcf.chat.server.constant.ServerConstant;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/2/2021 9:04 PM
 */
public class UserService {

    public void login(User user) {
        if (!ServerConstant.PASSWORD.equals(user.getPassword())) {
            throw new IllegalArgumentException("账户或者密码错误");
        }


    }

}
