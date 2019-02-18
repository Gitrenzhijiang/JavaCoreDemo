package com.test.unit8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class ByteArrayJavaClass extends SimpleJavaFileObject {

    public ByteArrayJavaClass(String name) {
        super(URI.create("bytes:///" + name), Kind.CLASS);
        stream = new ByteArrayOutputStream();
    }
    public OutputStream openOutputStream() throws IOException{
        return stream;
    }
    public byte[] getBytes() {
        return stream.toByteArray();
    }
    private ByteArrayOutputStream stream;
}
