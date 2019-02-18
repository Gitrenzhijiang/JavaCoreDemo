package com.test.unit9;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 加载
 * @author REN
 *
 */
public class PluginClassLoader {
    public static void main(String[] args) {
        try {
            test_loader();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    public static void test_loader() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        URL url = new URL("file:///D://pu.jar");
        URLClassLoader pluginLoader = new URLClassLoader(new URL[] {url});
        Class<?> cl = pluginLoader.loadClass("p.A");
        System.out.println(cl.getName());
        for (Method m:cl.getDeclaredMethods()) {
            System.out.println(m.invoke(cl.newInstance(), null));
        }
        //Class.forName("p.A"); // 这里会抛出异常，因为应用类加载器找不到这个类
        
    }
    public void test() {
        System.out.println(PluginClassLoader.class);
        System.out.println(this.getClass());
        System.out.println(this.getClass().equals(PluginClassLoader.class));
    }
}
