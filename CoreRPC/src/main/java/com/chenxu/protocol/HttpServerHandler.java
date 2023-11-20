package com.chenxu.protocol;

import com.chenxu.common.Invocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;

public class HttpServerHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp){
        // 处理请求: 接口、方法、参数
        // 服务端获取客户端发来的Invocation对象
        try {
            Object o = new ObjectInputStream(req.getInputStream()).readObject();
            Invocation invocation = (Invocation) o;
            // 拿到这些信息后，还要去接口的实现类中才能真正调用方法
            String interfaceName = invocation.getInterfaceName();
            String methodName = invocation.getMethodName();
            Class[] paramTypes = invocation.getParamTypes();
            Object[] paramValues = invocation.getParamValues();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
