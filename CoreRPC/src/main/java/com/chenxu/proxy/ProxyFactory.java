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

                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(),
                        method.getParameterTypes(), args);

                HttpClient httpClient = new HttpClient();
//                String result = httpClient.send("localhost", 8080, invocation);

                // 服务发现，获取可提供服务的列表
                List<URL> list = RemoteRegister.get(interfaceClass.getName());

                // 负载均衡
                URL url = LoadBalance.random(list);

                // 服务调用
                String result = httpClient.send(url.getHostName(), url.getPort(), invocation);

                return result;
            }
        });

        return (T) proxyInstance;
    }
}
