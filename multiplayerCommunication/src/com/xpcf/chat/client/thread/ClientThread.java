package com.xpcf.chat.client.thread;

import com.xpcf.chat.client.context.ClientContext;
import com.xpcf.chat.common.MessageEnum;
import com.xpcf.chat.common.entity.Message;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/4/2021 1:03 PM
 */
public class ClientThread extends Thread {



    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message) ClientContext.objectInputStream.readObject();

                switch (message.getType()) {
                    case MESSAGE_RET_ONLINE_FRIEND:
                        processOnlineFriends(message);
                        break;
                    case MESSAGE_COMM_MES:
                        processCommonMsg(message);
                        break;
                    case MESSAGE_BROADCAST:
                        processBroadcastMsg(message);
                        break;
                    case MESSAGE_SERVER:
                        processServerMsg(message);
                        break;
                    case MESSAGE_FILE:
                        processFileMsg(message);
                        break;
                    case MESSAGE_CLIENT_EXIT:
                        processExit(message);
                }


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                System.out.println("消息接收线程结束,socket关闭");
                break;
            }
        }
    }

    private void processExit(Message message) {
        Socket socket = ClientContext.getCurSocket();

        try {
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
            throw new IllegalStateException("socket 关闭");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void processOnlineFriends(Message message) {
//        System.out.println("在线用户" + message.getContent());
        String[] s = message.getContent().split(" ");

        ClientContext.outLock.lock();
        System.out.println("\n=======用户列表========");
        for (int i = 0; i < s.length; i++) {
            System.out.println("用户："+s[i]);
        }
        ClientContext.outLock.unlock();
    }

    private void processCommonMsg(Message message) {
        ClientContext.outLock.lock();
        System.out.print("\n收到\"" + message.getSender() + "\"发来的私人消息\n");
        System.out.println(message.getContent());
        ClientContext.outLock.unlock();
    }

    private void processBroadcastMsg(Message message) {
        ClientContext.outLock.lock();
        StringBuilder sb = new StringBuilder();
        sb.append("\n收到\"").append(message.getSender()).append("\"发来的广播消息\n")
                .append(" ").append(message.getContent());
        System.out.println(sb.toString());
        ClientContext.outLock.unlock();
    }

    private void processServerMsg(Message message) {
        ClientContext.outLock.lock();
        StringBuilder sb = new StringBuilder();
        sb.append("\n收到\"").append(message.getSender()).append("\"发来的推送消息\n")
                .append(" ").append(message.getContent());
        System.out.println(sb.toString());
        ClientContext.outLock.unlock();
    }

    private void processFileMsg(Message message) {
        byte[] bytes = message.getFileBytes();
        String dest = message.getContent();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(dest);
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ClientContext.outLock.lock();
        System.out.print("你接受到" + message.getSender() + "的文件，保存在:" + dest);
        ClientContext.outLock.unlock();
    }
}
