package com.xpcf.chat.client.context;

import com.xpcf.chat.common.ApplicationContext;
import com.xpcf.chat.common.entity.User;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 * client的全局上下文
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 11:33 PM
 */
public class ClientContext extends ApplicationContext {

    private static Thread ConnectThread;

    private static Socket curSocket;

    private static User curUser;

    public static Lock outLock = new ReentrantLock(false);

    public static ObjectOutputStream objectOutputStream;

    public static ObjectInputStream objectInputStream;

    public static User getCurUser() {
        return curUser;
    }

    public static void setCurUser(User curUser) {
        ClientContext.curUser = curUser;
    }

    public static Thread getConnectThread() {
        return ConnectThread;
    }

    public static void setConnectThread(Thread connectThread) {
        ConnectThread = connectThread;
    }

    public static Socket getCurSocket() {
        return curSocket;
    }

    public static void setCurSocket(Socket curSocket) {
        ClientContext.curSocket = curSocket;
    }

    public static InputStream getInputStream() throws IOException {
        return curSocket.getInputStream();
    }

    public static OutputStream getOutputStream() throws IOException {
        return curSocket.getOutputStream();
    }
}
