package com.chenxu;

import com.chenxu.protocol.HttpServer;

public class Provider {
    public static void main(String[] args) {
        // 收发网络请求，Netty,Tomcat,Socket等等
        // TODO 交给用户自定义实现网络请求工具，设计模式的运用

        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
