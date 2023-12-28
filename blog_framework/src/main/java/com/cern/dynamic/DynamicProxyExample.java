package com.cern.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 定义接口
interface UserService {
    void addUser(String username);
}

// 实现接口的具体类
class UserServiceImpl implements UserService {
    public void addUser(String username) {
        System.out.println("添加用户：" + username);
    }
}

// 实现InvocationHandler接口
class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理前置操作");
        Object result = method.invoke(target, args);
        System.out.println("动态代理后置操作");
        return result;
    }
}

public class DynamicProxyExample {
    public static void main(String[] args) {
        // 创建目标对象
        UserService userService = new UserServiceImpl();

        // 创建InvocationHandler实例
        MyInvocationHandler handler = new MyInvocationHandler(userService);

        // 创建动态代理对象
        UserService proxy = (UserService) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                // 被代理类实现的接口列表
                userService.getClass().getInterfaces(),
                handler
        );

        // 通过代理对象调用方法
        proxy.addUser("Alice");
    }
}