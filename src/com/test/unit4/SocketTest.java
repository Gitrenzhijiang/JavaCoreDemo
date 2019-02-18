package com.test.unit4;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class SocketTest {

    public static void main(String[] args) throws UnknownHostException, IOException {
        // Socket的这个构造器会一直阻塞直到连接成功
         
        
        // SocketChannel 它不会一直阻塞当等待数据时，它会响应 isInterruppted方法
        try(SocketChannel sc =  SocketChannel.open(new InetSocketAddress("time-a.nist.gov", 13));
                Scanner in = new Scanner(sc, "UTF-8"))
        {
            // socket的读操作会一直阻塞直到数据到来
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line); 
            }
        }
        InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
        for (InetAddress address : addresses)
        {
            System.out.println(address.getHostAddress());
        }
    }

}
