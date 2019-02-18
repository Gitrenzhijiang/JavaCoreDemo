package com.web.part2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * jdk动态代理实例
 * @author REN
 *
 */
public class JdkProxyExample implements InvocationHandler{
    
    public static void main(String[] args) {
        JdkProxyExample jpe = new JdkProxyExample();
        HelloWorld hw = jpe.bind(new HelloWorldImp());
        hw.hello();
        ((Test)hw).test();
    }
    private Object hw;
    public JdkProxyExample() {}
    
    public HelloWorld bind(Object hw) {
        this.hw = hw;
        return (HelloWorld) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {
                HelloWorld.class, Test.class}, this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke before...");
        Object ret = method.invoke(hw, args);
        System.out.println("invoke after...");
        return ret;
    }
    
}
interface HelloWorld{
    void hello();
}
interface Test{
    void test();
}
class HelloWorldImp implements HelloWorld, Test{

    @Override
    public void hello() {
        System.out.println("hello, world");
    }

    @Override
    public void test() {
        System.out.println("Test...");
    }
    
}
