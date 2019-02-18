package com.test.unit8;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyDemo {

    public static void main(String[] args) {
        
    }
    public static void addListener(Object source, final Object param, final Method m) {
        InvocationHandler handler = new InvocationHandler() {
            
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                
                return method.invoke(param);
            }
        };
        
    }

}
