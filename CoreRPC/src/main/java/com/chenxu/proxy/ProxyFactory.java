package com.chenxu.proxy;

import com.chenxu.common.Invocation;
import com.chenxu.common.URL;
import com.chenxu.loadbalance.LoadBalance;
import com.chenxu.protocol.HttpClient;
import com.chenxu.register.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactory {

    public static <T> T getProxy(Class interfaceClass) {
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] args) throws Throwable {

                // Mock:被调用的方法还没开发完，调用方先把调用逻辑写好，返回结果值先自定义
                String mock = System.getProperty("mock");
                if (mock != null && mock.startsWith("return:")) {
                    String result = mock.replace("return:", "");
                    return result;
                }

                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(),
                        method.getParameterTypes(), args);

                HttpClient httpClient = new HttpClient();
//                String result = httpClient.send("localhost", 8080, invocation);

                // 服务发现，获取可提供服务的列表
                List<URL> list = RemoteRegister.get(interfaceClass.getName());

                // 负载均衡
                URL url = LoadBalance.random(list);

                // 服务调用
                // TODO 服务重试：调用一次失败后再换个URL调用，达到指定次数max后再走容错逻辑
                String result = null;
                try {
                    result = httpClient.send(url.getHostName(), url.getPort(), invocation);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 服务容错：发生错误后的执行逻辑
                    // Error Callback: com.chenxu.HelloServiceErrorCallback implements HelloService
                    return "服务调用失败...";
                }

                return result;
            }
        });

        return (T) proxyInstance;
    }
}
