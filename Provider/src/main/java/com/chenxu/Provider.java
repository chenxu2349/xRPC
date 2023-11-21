package com.chenxu;

import com.chenxu.protocol.HttpServer;
import com.chenxu.register.LocalRegister;

public class Provider {
    public static void main(String[] args) {
        // 收发网络请求，Netty,Tomcat,Socket等等
        // TODO 交给用户自定义实现网络请求工具，设计模式的运用

        // 服务的本地注册，也可以存的时候key带上版本号version用于存放一个服务接口的多版本实现，如HelloServiceImpl2,HelloServiceImpl3...
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class);

        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
