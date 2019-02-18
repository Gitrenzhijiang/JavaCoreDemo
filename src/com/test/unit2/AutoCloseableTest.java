package com.test.unit2;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PushbackInputStream;

public class AutoCloseableTest {

    public static void main(String[] args) throws Exception {
        try (
                SRes sres = new SRes();
                
                RRes rres = new RRes();
                
                ){
            System.out.println("this is a try-with-resource语句:here you can use sres and rres");
        }catch (Exception e) {
            System.out.println("catch a exception:" + e.getMessage());
        }
        String home = "";
        System.out.println("当前工作目录:"+(home=System.getProperty("user.dir")));
        DataInputStream dis = new DataInputStream(
                new PushbackInputStream(new BufferedInputStream(
                        new FileInputStream(home+File.separator+"nums.txt"))));
        
        
        PushbackInputStream pi = new PushbackInputStream(dis);
        System.out.println(pi.read());
        pi.unread(1);
        System.out.println(pi.read());
    }
    private static class SRes implements AutoCloseable{

        @Override
        public void close() throws Exception {
            System.out.println("SRes close...");
            throw new RuntimeException("test exception");
        }
        
    }
    private static class RRes implements AutoCloseable{

        @Override
        public void close() throws Exception {
            System.out.println("RRes close...");
        }
        
    }
}
