package com.xpcf.chat.client.view;


import com.xpcf.chat.client.context.ChoiceFactory;
import com.xpcf.chat.common.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 10:46 PM
 */
public class ChatView {

    public static boolean RUNNING = true;

    public static void main(String[] args) throws IOException {
        new ChatView().start();

    }

    public ChatView() {
        init();
    }

    private void init() {

    }

    public void start() {

        while (RUNNING) {

            try {
                System.out.println("==========欢迎登录网络通讯系统");
                System.out.println("1 表示登录系统");
                System.out.println("9 表示退出系统");
                System.out.print("请输入您的选择：");
                String s = Utility.readString(1);
                ChoiceFactory.getChoice(s).execute();
            } catch (IOException e) {
                System.out.println("程序异常退出");
                e.printStackTrace();
            }

        }
        System.out.println("程序即将退出");
    }


}
