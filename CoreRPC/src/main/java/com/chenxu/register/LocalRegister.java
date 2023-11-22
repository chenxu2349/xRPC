package com.chenxu.register;

import java.util.HashMap;
import java.util.Map;

public class LocalRegister {
    private static Map<String, Class> map = new HashMap<>();

    // 注册服务
    public static void register(String interfaceName, Class implClass) {
        map.put(interfaceName, implClass);
    }

    // 获取服务
    public static Class get(String interfaceName) {
        return map.get(interfaceName);
    }
}
