package com.chenxu;

import com.chenxu.common.Invocation;
import com.chenxu.protocol.HttpClient;
import com.chenxu.proxy.ProxyFactory;

public class Consumer {

    public static void main(String[] args) {

        // 调用方法一：动态代理
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.sayHello("tom"); // 调用这里的方法会进到proxyinstance里的invoke
        System.out.println(result);

        // 调用方法二
        // Invocation对象通过请求的方式发送出去
//        Invocation invocation = new Invocation(HelloService.class.getName(), "sayHello",
//                new Class[]{String.class}, new Object[]{"chenxu"});
//
//        HttpClient httpClient = new HttpClient();
//        String result = httpClient.send("localhost", 8080, invocation);
//        System.out.println(result);
    }
}
