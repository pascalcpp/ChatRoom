package com.xpcf.chat.server.context;

import com.xpcf.chat.common.ApplicationContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/2/2021 8:42 PM
 */
public class ServerContext extends ApplicationContext {

    public static Map<String, Socket> socketMap = new ConcurrentHashMap<>();

    public static Map<String, ObjectOutputStream> outputMap = new ConcurrentHashMap<>();

    public static Map<String, ObjectInputStream> inputMap = new ConcurrentHashMap<>();

    public static Thread pushThread;

    public static boolean RUNNING = true;

    private static ExecutorService serverThreadPool = new ThreadPoolExecutor(5, 100,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new ThreadPoolExecutor.AbortPolicy());


    public static ExecutorService getServerThreadPool() {
        return serverThreadPool;
    }

    public static void setServerThreadPool(ExecutorService serverThreadPool) {
        ServerContext.serverThreadPool = serverThreadPool;
    }

//    public static ThreadLocal<Socket> socketThreadLocal = new ThreadLocal<>();
//
//    public static ThreadLocal<ObjectOutputStream> outputStreamThreadLocal = new ThreadLocal<>();
//
//    public static ThreadLocal<ObjectInputStream> inputStreamThreadLocal = new ThreadLocal<>();


//    public static ThreadLocal<Socket> getSocketThreadLocal() {
//        return socketThreadLocal;
//    }
//
//    public static void setSocketThreadLocal(ThreadLocal<Socket> socketThreadLocal) {
//        ServerContext.socketThreadLocal = socketThreadLocal;
//    }
//

//
//    /**
//     * 根据 userid 返回对应socket的outputstream
//     *
//     * @param key
//     * @return
//     * @throws IOException
//     */
//    public static OutputStream getOutputByKey(String key) throws IOException {
//        return socketMap.get(key).getOutputStream();
//    }
//
//    public static Map<String, Socket> getSocketMap() {
//        return socketMap;
//    }
//
//    public static void setSocketMap(Map<String, Socket> socketMap) {
//        ServerContext.socketMap = socketMap;
//    }
}
