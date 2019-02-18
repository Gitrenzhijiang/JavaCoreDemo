package com.test.unit4;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class URLTest {

    public static void main(String[] args) throws Exception {
//         testURL();
        testURLConnection();
    }
    public static void testURLConnection() throws Exception {
        // URLConnection 可以从某个web 资源处获得更多的信息
        URL url = new URL("https://www.baidu.com/");
        // 创建连接对象
        URLConnection connection = url.openConnection();
        
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("user", "zhijang");
        // 与远程对象的实际连接
        connection.connect();
        String key = connection.getHeaderFieldKey(2);
        System.out.println(key + ":" + connection.getHeaderField(2));
        Scanner sc = new Scanner(connection.getInputStream());
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
    }
    
    public static void testURL() throws Exception {
        URL url = new URL("http://i.baidu.com/"); // 可以获得资源的输入流
        Scanner sc = new Scanner(url.openStream());
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
    }
}
