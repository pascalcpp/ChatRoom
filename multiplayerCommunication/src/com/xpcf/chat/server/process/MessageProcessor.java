package com.xpcf.chat.server.process;

import com.xpcf.chat.common.MessageConstant;
import com.xpcf.chat.common.MessageEnum;
import com.xpcf.chat.common.entity.Message;
import com.xpcf.chat.server.constant.ServerConstant;
import com.xpcf.chat.server.context.ServerContext;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/2/2021 9:13 PM
 */
public class MessageProcessor {

    public static void processMessage(Message message) throws IOException {
        switch (message.getType()) {
            case MESSAGE_GET_ONLINE_FRIEND:
                getOnlineUser(message);
                break;
            case MESSAGE_BROADCAST:
                sendBroadcast(message);
                break;
            case MESSAGE_COMM_MES:
                sendPrivateMsg(message);
                break;
            case MESSAGE_FILE:
                processFileMsg(message);
                break;
            case MESSAGE_CLIENT_EXIT:
                clientExit(message);
        }
    }

    private static void processFileMsg(Message message) throws IOException {
        String receiver = message.getReceiver();
        ServerContext.outputMap.get(receiver).writeObject(message);
        StringBuilder sb = new StringBuilder();
        System.out.println(sb.append(message.getSender()).append(" 向 ").append(receiver).append("发送文件"));
    }

    /**
     * client 请求退出
     *
     * @param message
     */
    private static void clientExit(Message message) throws IOException {
        String sender = message.getSender();
        Socket socket = ServerContext.socketMap.get(sender);


        Message res = new Message();
        res.setType(MessageEnum.MESSAGE_CLIENT_EXIT);
        ServerContext.outputMap.get(sender).writeObject(res);
        socket.shutdownInput();
        socket.shutdownOutput();
        socket.close();
        removeFromMap(sender);
        throw new IllegalStateException("socket 关闭");

    }

    private static void removeFromMap(String userId) {
        ServerContext.socketMap.remove(userId);
        ServerContext.outputMap.remove(userId);
        ServerContext.inputMap.remove(userId);
    }

    private static void sendPrivateMsg(Message message) throws IOException {
        String sender = message.getSender();
        String receiver = message.getReceiver();

        ObjectOutputStream outputStream = ServerContext.outputMap.get(receiver);

        outputStream.writeObject(message);
        System.out.println(sender + "发送给" + receiver);
    }

    private static void sendBroadcast(Message message) {
        String sender = message.getSender();
        ServerContext.outputMap.entrySet().stream().filter(entry -> entry.getKey() != sender)
                .forEach((entry) -> {
                    Message res = new Message(sender, entry.getKey(), message.getContent(), MessageEnum.MESSAGE_BROADCAST);
                    try {
                        entry.getValue().writeObject(res);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(sender + "发送广播消息");
    }


    /**
     * 返回在线用户
     *
     * @param message
     */
    private static void getOnlineUser(Message message) throws IOException {
        System.out.println(message.getSender() + " 尝试获取当前在线用户");

        String sender = message.getSender();

        ObjectOutputStream outputStream = ServerContext.outputMap.get(sender);
        StringBuilder sb = new StringBuilder();
        ServerContext.socketMap.keySet().forEach((key) -> {
            if (!key.equals(message.getSender())) {
                sb.append(key).append(" ");
            }
        });

        String onlines = sb.toString();
        Message response = new Message(ServerConstant.SERVER_NAME, sender, onlines, MessageEnum.MESSAGE_RET_ONLINE_FRIEND);
        System.out.println(message.getSender() + " 在线用户 " + onlines);
        outputStream.writeObject(response);
        System.out.println(message.getSender() + " 成功返回在线用户");


    }
}
