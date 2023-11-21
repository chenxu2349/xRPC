package com.chenxu.register;

import com.chenxu.common.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteRegister {

    // List<URL>存储的是服务集群地址
    private static Map<String, List<URL>> map = new HashMap<>();

    public static void register(String interfaceName, URL url) {
        List<URL> list = map.get(interfaceName);
        if (list == null) list = new ArrayList<>();
        list.add(url);
        map.put(interfaceName, list);
    }

    public static List<URL> get(String interfaceName) {
        return map.get(interfaceName);
    }
}
