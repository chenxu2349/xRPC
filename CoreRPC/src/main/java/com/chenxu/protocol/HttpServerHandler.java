package com.chenxu.protocol;

import com.chenxu.common.Invocation;
import com.chenxu.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

            Class implClass = LocalRegister.get(interfaceName);
            Method method = implClass.getMethod(methodName, paramTypes);
            String result = (String) method.invoke(implClass.newInstance(), paramValues);

            // 得到结果，写入response并返回
            IOUtils.write(result, resp.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
