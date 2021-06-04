package com.xpcf.chat.client.context;

import com.xpcf.chat.client.context.constant.ChoiceConstant;
import com.xpcf.chat.client.context.impl.ChoiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XPCF
 * @version 1.0
 * @date 6/1/2021 11:00 PM
 */
public class ChoiceFactory {

    private static Map<String, ClientChoice> map = new HashMap<>();

    static {
        map.put(ChoiceConstant.LOGIN, new ChoiceImpl.LoginChoice());
        map.put(ChoiceConstant.EXIT, new ChoiceImpl.ExitChoice());
        map.put(ChoiceConstant.BROADCAST, new ChoiceImpl.BroadcastChoice());
        map.put(ChoiceConstant.FILE, new ChoiceImpl.FileChoice());
        map.put(ChoiceConstant.PRIVATE, new ChoiceImpl.PrivateChoice());
        map.put(ChoiceConstant.SHOWONLINE, new ChoiceImpl.ShowChoice());
    }

    public static ClientChoice getChoice(String key) {
        return map.get(key);
    }
}
