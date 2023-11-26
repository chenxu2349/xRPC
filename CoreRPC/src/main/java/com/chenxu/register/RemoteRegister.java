package com.chenxu.register;

import com.chenxu.common.URL;

import java.io.*;
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

        saveFile();
    }

    public static List<URL> get(String interfaceName) {

        map = getFile();

        return map.get(interfaceName);
    }

    // 用文件的形式解决进程间数据共享问题
    public static void saveFile() {
        try {
//            File file = new File("/temp.txt");
            // Linux必须写全绝对路径！！！害惨我了！！！
//            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\chenxu\\Desktop\\研一\\GitHub\\xRPC\\temp.txt");
            FileOutputStream fileOutputStream = new FileOutputStream("/home/chenxu/Desktop/IdeaProjects/xRPC/temp.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, List<URL>> getFile() {
        try {
//            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\chenxu\\Desktop\\研一\\GitHub\\xRPC\\temp.txt");
            FileInputStream fileInputStream = new FileInputStream("/home/chenxu/Desktop/IdeaProjects/xRPC/temp.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
