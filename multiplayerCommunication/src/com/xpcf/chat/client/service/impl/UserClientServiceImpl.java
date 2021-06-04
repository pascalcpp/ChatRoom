package com.xpcf.chat.client.service.impl;

import com.xpcf.chat.client.context.ClientContext;
import com.xpcf.chat.client.context.constant.ClientConstant;
import com.xpcf.chat.client.service.UserClientService;
import com.xpcf.chat.client.thread.ClientThread;
import com.xpcf.chat.common.MessageEnum;
import com.xpcf.chat.common.Utility;
import com.xpcf.chat.common.entity.Message;
import com.xpcf.chat.common.entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 11:30 PM
 */
public class UserClientServiceImpl implements UserClientService {

    @Override
    public void checkUser(String userId, String password) throws IOException {
        try {

            Socket socket = new Socket(Utility.getValue(ClientConstant.serverAddr), Integer.valueOf(Utility.getValue(ClientConstant.port)));
            ClientContext.setCurSocket(socket);
            User user = new User(userId, password);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeObject(user);
            Message message = (Message) inputStream.readObject();

            if (MessageEnum.MESSAGE_LOGIN_SUCCEED == message.getType()) {
                ClientContext.setCurUser(user);
                ClientContext.objectOutputStream = outputStream;
                ClientContext.objectInputStream = inputStream;
                processLoginSuccess();
            } else {
                socket.close();
                throw new IllegalArgumentException("用户名或密码错误");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void processLoginSuccess() {
        ClientThread thread = new ClientThread();
        thread.start();
        ClientContext.setConnectThread(thread);

    }





}
