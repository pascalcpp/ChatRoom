package com.xpcf.chat.server.core;

import com.xpcf.chat.client.context.constant.ClientConstant;
import com.xpcf.chat.common.MessageConstant;
import com.xpcf.chat.common.MessageEnum;
import com.xpcf.chat.common.Utility;
import com.xpcf.chat.common.entity.Message;
import com.xpcf.chat.common.entity.User;
import com.xpcf.chat.server.constant.ServerConstant;
import com.xpcf.chat.server.context.ServerContext;
import com.xpcf.chat.server.process.MessageProcessor;
import com.xpcf.chat.server.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 11:44 PM
 */
public class ChatServer {

    private UserService userService;

    public static void main(String[] args) {

        new ChatServer().start();
    }

    public ChatServer() {
        init();
    }

    /**
     * TODO
     */
    private void init() {
        userService = new UserService();
    }

    public void start() {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(Integer.parseInt(Utility.getValue(ServerConstant.PORT)));
            System.out.println("服务器启动，在PORT： " + Utility.getValue(ServerConstant.PORT) + " 监听");
            System.out.println("输入9可以推送消息");
            Thread thread = new Thread(() -> {
                while (ServerContext.RUNNING) {
                    if ("9".equals(Utility.readString(1))) {
                        String content = Utility.readString(50);
                        ServerContext.outputMap.entrySet().stream().forEach((entry) -> {
                                    Message res = new Message(ServerConstant.SERVER_NAME, entry.getKey(), content, MessageEnum.MESSAGE_SERVER);
                                    try {
                                        entry.getValue().writeObject(res);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                        System.out.println("成功推送消息");
                    }
                }
            });
            thread.start();
            ServerContext.pushThread = thread;

        } catch (IOException e) {
            System.out.println("监听端口时出现异常");
            e.printStackTrace();
        }

        try {
            run(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("socket 出现异常");
        }
    }

    private void run(ServerSocket serverSocket) throws IOException {


        while (true) {
            Message response = new Message();
            Socket socket = serverSocket.accept();
            ObjectInputStream inputStream;
            ObjectOutputStream outputStream = null;
            User user = null;
            try {

                inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream = new ObjectOutputStream(socket.getOutputStream());

                user = (User) inputStream.readObject();
                String userId = user.getUserId();
                System.out.println(userId + "尝试登陆");
                userService.login(user);

                System.out.println(userId + "登陆成功");

                response.setType(MessageEnum.MESSAGE_LOGIN_SUCCEED);
                outputStream.writeObject(response);
                // 将socket放入 map  固定 outputstrea

                // 将相应对象加入 map
                ServerContext.socketMap.put(userId, socket);
                ServerContext.outputMap.put(userId, outputStream);
                ServerContext.inputMap.put(userId, inputStream);

                ServerContext.getServerThreadPool().execute(() -> {

                    while (true) {
                        try {
                            Message message = (Message) inputStream.readObject();
                            MessageProcessor.processMessage(message);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("socket io出现异常");
                            break;
                        } catch (IllegalStateException e) {
                            System.out.println("socket 关闭 线程结束");
                            break;
                        }
                    }

                });

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println(user.getUserId() + "登陆失败");
                response.setType(MessageEnum.MESSAGE_LOGIN_FAIL);
                outputStream.writeObject(response);
                socket.close();
            }

        }

    }
}
