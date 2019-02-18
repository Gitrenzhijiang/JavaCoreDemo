package com.test.unit9;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * 自定义的类加载器
 * 明确类是由类加载器和类的全名决定类的唯一性(在同一个虚拟机下)
 * @author REN
 *
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
//        Path path = Paths.get("build");
//        System.out.println(path.toFile().getAbsolutePath());
        ClassLoader cloader= new ClassLoader() {

            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                byte[] bts = null;
                try {
                    bts = loadClassBytes(name);
                    System.out.println("load class:" + name + "-->ok");
                } catch (IOException e) {
                    // 如果没有这个文件，使用系统类加载器
                    System.out.println("system loader:" + name + "-->ok");
                    return super.loadClass(name);
                }
               return defineClass(name, bts, 0, bts.length);
            }
            
            private byte[] loadClassBytes(String name) throws IOException {
                String cname = name.replace(".", File.separator) + ".class";
                byte[] bytes = Files.readAllBytes(Paths.get("build\\classes\\"+cname));
                return bytes;
            }
            
        };
        Class<?> cz = cloader.loadClass("com.test.unit9.PluginClassLoader");
        Object obj = cz.newInstance();
        System.out.println(obj instanceof PluginClassLoader);
        Method m = PluginClassLoader.class.getMethod("test");
        m.invoke(obj);
    }
}
