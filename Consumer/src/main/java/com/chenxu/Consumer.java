package com.chenxu;

public class Consumer {

    public static void main(String[] args) {
        HelloService helloService = ?;
        String result = helloService.sayHello("tom");
        System.out.println(result);
    }
}
