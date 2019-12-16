package org.cdt.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author dataochen
 * @Description
 * @date: 2019/12/16 20:43
 */
public class JdkProxy<T> implements InvocationHandler {
    /**
     * 被代理的类
     */
    private T object;

    public JdkProxy(T object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(object,args);
    }

    public T getProxy() {
        return (T)Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

}
