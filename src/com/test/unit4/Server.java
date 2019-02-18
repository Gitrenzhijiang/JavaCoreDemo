package com.test.unit4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public final static int PORT = 18382;
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            while (true) {
                Socket socket = server.accept();
                new Thread(new HandleRunner(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class HandleRunner implements Runnable{
        private Socket socket;
        
        public HandleRunner(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            // 接受客户端发来的消息
            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
                Scanner scanner = new Scanner(in);
                PrintWriter pw = new PrintWriter(out);
                // 接受客户端的两行消息 并打印
                System.out.println(socket.getInetAddress()+":"+scanner.nextLine());
                System.out.println(socket.getInetAddress()+":"+scanner.nextLine());
                // 服务器一直等待客户端的输入流关闭
                Thread.sleep(3000);
                System.out.println("input shut down?"+socket.isInputShutdown());
                System.out.println("output shut down?"+socket.isOutputShutdown());
                // 反馈
                pw.println("server:ok, copy that");
                pw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        private InputStream in;
        private OutputStream out;
    }
}
