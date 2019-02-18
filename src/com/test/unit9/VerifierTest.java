package com.test.unit9;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
/**
 * Java SecurityManager 测试实例
 * @author REN
 *
 */
public class VerifierTest {

    public static void main(String[] args) throws Exception {
        System.setProperty("java.security.policy", "MyApp.policy");
        SecurityManager sm = new SecurityManager();
        System.setSecurityManager(sm); // 安装安全管理器
        System.out.println(sm);
        
        
        File file = Paths.get("D://a/123.txt").toFile();
        try (
                PrintWriter pw = new PrintWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(file, true)));    
                ){
            pw.append("hello,world");
        }
        
        
        
    }
    static int fun()
    {
        int m = 1;
        int n;
        m = 1; 
        n = 2; // 在n=2,的字节码变成m=2;
        int r = m + n;
        return r;
    }

}
