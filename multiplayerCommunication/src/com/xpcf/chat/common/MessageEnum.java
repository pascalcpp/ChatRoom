package com.xpcf.chat.common;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 10:39 PM
 */
public enum MessageEnum {

    /**
     *
     */
    MESSAGE_LOGIN_SUCCEED(1, "登陆成功"),

    MESSAGE_LOGIN_FAIL(2, "登陆失败"),

    MESSAGE_COMM_MES(3, "普通消息"),

    MESSAGE_GET_ONLINE_FRIEND(4, "请求在线用户"),

    MESSAGE_RET_ONLINE_FRIEND(5, "返回在线用户"),

    MESSAGE_CLIENT_EXIT(6, "client请求退出"),

    MESSAGE_SERVER(7, "server推送消息"),

    MESSAGE_BROADCAST(8, "群发消息"),

    MESSAGE_FILE(9, "文件消息")
    ;

    /**
     * status code
     */
    private final int code;

    /**
     * status describe
     */
    private final String data;

    MessageEnum(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }
}
