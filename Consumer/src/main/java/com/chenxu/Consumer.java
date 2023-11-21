package com.chenxu;

import com.chenxu.common.Invocation;
import com.chenxu.protocol.HttpClient;

public class Consumer {

    public static void main(String[] args) {
//        HelloService helloService = ?;
//        String result = helloService.sayHello("tom");
//        System.out.println(result);

        // Invocation对象通过请求的方式发送出去
        Invocation invocation = new Invocation(HelloService.class.getName(), "sayHello",
                new Class[]{String.class}, new Object[]{"chenxu"});

        HttpClient httpClient = new HttpClient();
        String result = httpClient.send("localhost", 8080, invocation);
        System.out.println(result);
    }
}
