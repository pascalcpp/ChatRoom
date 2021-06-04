package com.xpcf.chat.common.entity;

import com.xpcf.chat.common.MessageEnum;

import java.io.Serializable;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 11:32 PM
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sender;

    private String receiver;

    private String content;

    private byte[] fileBytes;

    /**
     * 代表消息类型
     */
    private MessageEnum type;

    private String sendTime;


    public Message(String sender, String receiver, String content, MessageEnum type, String sendTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.type = type;
        this.sendTime = sendTime;
    }

    public Message() {

    }

    public Message(String sender, String receiver, String content, MessageEnum type) {
        this(sender, receiver, content, type, null);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageEnum getType() {
        return type;
    }

    public void setType(MessageEnum type) {
        this.type = type;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}
