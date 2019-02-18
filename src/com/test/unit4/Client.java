package com.test.unit4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {
        try (Socket socket = new Socket("127.0.0.1", Server.PORT);){
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println("hello, i am xxx");
            pw.println("收到请回复..");
            pw.flush();
            socket.shutdownOutput();
            System.out.println("already close the outputstream:" + socket.isOutputShutdown());
            // 接受服务端的响应
            System.out.println(scanner.nextLine());
        }
                
    }

}
