package com.test.dynamic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Test {
    private void test() {
        for (int i = 0;i < 5;i++)
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa---test");
        
        
    }
    public static void main(String[] args) throws Exception {
        
//        /**
//         * 0. 把权限设计好
//         * 1. 将需要动态加载的.java文件编写好
//         * 2. 动态编译它
//         * 3. 使用类加载器加载这个.class文件
//         * 4. new 出对象, 使用它
//         */
        String filepath = "D://safeClasses//F1.java";
        // 输出编译后的文件到D://
        System.out.println("hello");
        JavaCompilerUtils.compile(Arrays.asList(filepath, "D://safeClasses//Fun.java"), "D://safeClasses//");
        System.out.println("word");
        MyTestClassLoader loader = new MyTestClassLoader();
        Class clazz = loader.loadClass("F1");
        Object obj = clazz.newInstance();
        
        // 这里要注意, 要和子进程的工作空间  匹配的路径.
        System.setProperty("java.security.policy", "com/test/dynamic/Permission.policy");
        System.setSecurityManager(new SecurityManager());
        // 下面两种方法 选用一种即可
//        clazz.getMethod("fun").invoke(obj);
      ((Fun)obj).fun();
        
        
        new Test().test();
//        System.out.println("Input file text:");
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                System.out.println("sub process:"+line);
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
//        File file = new File("D://okTest.txt");
//        if (file == null || !file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            FileWriter fw = new FileWriter(file);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write("aaaaaaaaaaaaaaaaaabbbbbbbbbbbbtest");
//            bw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
