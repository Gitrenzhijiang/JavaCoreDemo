package com.test.dynamic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

public class DynamicAlgorithm {
    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//        System.setProperty("java.security.policy", "src/com/test/dynamic/Permission.policy");
//        System.setSecurityManager(new MySecurityManager());
        
        
        try {
            ProcessBuilder processbuild = 
                    new ProcessBuilder(Arrays.asList("java", "com.test.dynamic.Test"));
//            Map<String, String> map = processbuild.environment();
//            for (String key : map.keySet()) {
//                System.out.println(key + "=" + map.get(key));
//            }
            // 运行之前设置 工作目录
            processbuild.directory(new File("D:\\myfile\\JavaSource\\w1\\JavaCoreDemo\\build\\classes"));
            // 设置进程的 inputstream
            //processbuild.redirectInput(new File("D://1.txt"));
//            processbuild.inheritIO();
            
            Process process = processbuild.start();
            
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println(process.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        System.out.println("game over");
    }

}
class MyTestClassLoader extends ClassLoader {
    /**
     * 所有编译好的文件都在这里
     */
    private static final String FOLDER = "D://safeClasses//"; 
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] b = Files.readAllBytes(Paths.get(FOLDER, name + ".class"));
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}