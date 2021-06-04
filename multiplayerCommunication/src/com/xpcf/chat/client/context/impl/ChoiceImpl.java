package com.xpcf.chat.client.context.impl;

import com.xpcf.chat.client.context.ChoiceFactory;
import com.xpcf.chat.client.context.ClientChoice;
import com.xpcf.chat.client.context.ClientContext;
import com.xpcf.chat.client.log.Logger;
import com.xpcf.chat.client.log.LoggerFactory;
import com.xpcf.chat.client.service.MessageClientService;
import com.xpcf.chat.client.service.UserClientService;
import com.xpcf.chat.client.service.impl.UserClientServiceImpl;
import com.xpcf.chat.client.view.ChatView;
import com.xpcf.chat.common.MessageConstant;
import com.xpcf.chat.common.MessageEnum;
import com.xpcf.chat.common.Utility;
import com.xpcf.chat.common.entity.Message;
import com.xpcf.chat.server.constant.ServerConstant;
import com.xpcf.chat.server.context.ServerContext;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 11:00 PM
 */
public class ChoiceImpl {

//    private static Logger logger = LoggerFactory.getLogger(ChoiceImpl.class);

    private static UserClientService userService = new UserClientServiceImpl();

    private static MessageClientService messageService;


    public static class LoginChoice implements ClientChoice {

        @Override
        public void execute() throws IOException {

            System.out.print("请输入用户id:");
            String userId = Utility.readString(50);
            System.out.print("请输入密码:");
            String password = Utility.readString(50);
            //调用验证服务
            try {
                userService.checkUser(userId, password);
            } catch (IllegalArgumentException e) {
                System.out.println("登录失败");
                throw e;
            }

            //
            System.out.println("=======欢迎(" + userId + ")登录成功============");
            System.out.println("网络通讯系统二级菜单");

            while (ChatView.RUNNING) {

                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientContext.outLock.lock();
                printMenu(userId);
                ClientContext.outLock.unlock();
                String sign = Utility.readString(1);
                ChoiceFactory.getChoice(sign).execute();

            }


        }
    }

    public static class ExitChoice implements ClientChoice {

        @Override
        public void execute() throws IOException {

            ObjectOutputStream outputStream = ClientContext.objectOutputStream;
            Message request = buildMessage("", MessageEnum.MESSAGE_CLIENT_EXIT);
            outputStream.writeObject(request);

            // stop
            ChatView.RUNNING = false;

        }
    }

    public static class ShowChoice implements ClientChoice {

        @Override
        public void execute() throws IOException {

            ObjectOutputStream outputStream = ClientContext.objectOutputStream;
            Message request = buildMessage("", MessageEnum.MESSAGE_GET_ONLINE_FRIEND);
            outputStream.writeObject(request);
            // close

        }
    }

    public static class BroadcastChoice implements ClientChoice {

        @Override
        public void execute() throws IOException {
            System.out.print("请输入你的消息内容： ");
            String content = Utility.readString(50);
            ObjectOutputStream outputStream = ClientContext.objectOutputStream;
            Message request = buildMessage(content, MessageEnum.MESSAGE_BROADCAST);
            outputStream.writeObject(request);
        }
    }

    public static class PrivateChoice implements ClientChoice {

        @Override
        public void execute() throws IOException {

            System.out.print("请输入消息接收用户： ");
            String receiver = Utility.readString(50);
            System.out.print("请输入你的消息内容： ");
            String content = Utility.readString(50);
            ObjectOutputStream outputStream = ClientContext.objectOutputStream;
            Message request = buildMessage(content, MessageEnum.MESSAGE_COMM_MES, receiver);
            outputStream.writeObject(request);

        }
    }

    public static class FileChoice implements ClientChoice {

        @Override
        public void execute() throws IOException {
            System.out.print("请对方用户名： ");
            String receiver = Utility.readString(50);
            System.out.print("请输入文件的绝对路径： ");
            String src = Utility.readString(100);
            System.out.print("请输入对方接受文件路径： ");
            String dest = Utility.readString(100);
            Message message = buildMessage(dest, MessageEnum.MESSAGE_FILE, receiver);
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(src);
                int len = 0;
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((len = fileInputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                message.setFileBytes(baos.toByteArray());
                ClientContext.objectOutputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }
    }

    private static Message buildMessage(String content, MessageEnum messageEnum) {
        return new Message(ClientContext.getCurUser().getUserId(), ServerConstant.SERVER_NAME, content, messageEnum);
    }

    private static Message buildMessage(String content, MessageEnum messageEnum, String receiver) {
        if (receiver == null) {
            return buildMessage(content, messageEnum);
        }
        return new Message(ClientContext.getCurUser().getUserId(), receiver, content, messageEnum);
    }

    private static void printMenu(String userId) {
        System.out.println("\n=========网络通信系统二级菜单：用户(" + userId + ")");
        System.out.println("\t\t 2 显示在线用户列表");
        System.out.println("\t\t 3 群发消息");
        System.out.println("\t\t 4 私聊消息");
        System.out.println("\t\t 5 发送文件");
        System.out.println("\t\t 9 退出系统");
        System.out.print("请输入您的选择：");
    }


}
