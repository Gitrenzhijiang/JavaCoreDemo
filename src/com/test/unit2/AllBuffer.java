package com.test.unit2;

import java.nio.ByteBuffer;

public class AllBuffer {

    public static void main(String[] args) {
        ByteBuffer buff = ByteBuffer.allocate(10);
        for (int i = 0;i < buff.limit();i++)
            buff.put((byte)i);
        for (int i = 0;i < buff.limit();i++) {
            System.out.println(buff.get(i));
        }
        buff.flip();
        for (int i = 0;i < buff.limit();i++)
            buff.put((byte)i);
    }
    
}
