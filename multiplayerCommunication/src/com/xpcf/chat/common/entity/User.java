package com.xpcf.chat.common.entity;

import java.io.Serializable;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 11:32 PM
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String password;

    public User() {

    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
