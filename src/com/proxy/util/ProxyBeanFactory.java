package com.proxy.util;
/**
 * 代理对象生成工厂
 * @author REN
 *
 */
public class ProxyBeanFactory {
    public static <T> T getBean(T obj, Interceptor interceptor) {
        return ProxyBeanUtil.getBean(obj, interceptor);
    }
}
