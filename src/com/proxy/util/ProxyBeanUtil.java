package com.proxy.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyBeanUtil implements InvocationHandler{
    private Object obj; //实际对象
    private Interceptor interceptor;
    
    @SuppressWarnings("unchecked")
    public static <T> T getBean(T object, Interceptor interceptor) {
        if (object == null) {
            return null;
        }
        if (interceptor == null) {
            return object;
        }
        ProxyBeanUtil _this = new ProxyBeanUtil();
        _this.obj = object;
        _this.interceptor = interceptor;
        return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), _this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 再所有的方法执行之前
        boolean isthrow = false;
        interceptor.before(obj);
        Object ret = null;
        try {
            ret = method.invoke(obj, args);
        } catch (Exception e) {
            isthrow = true;
        }finally {
            interceptor.after(obj);
        }
        if (isthrow) {
            interceptor.afterThrowing(obj);
        }else {
            interceptor.afterReturning(obj);
        }
        return ret;
    }
}
