package com.test.unit2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DeepCloneBySerializable {

    public static void main(String[] args) throws CloneNotSupportedException {
        
        // 这是浅拷贝 
        // 下面clone方法实现了利用SerialCloneable
        Demo demo = new Demo();
        SerialCloneable sc = new SerialCloneable(); sc.demo = demo;
        SerialCloneable sc_copy = (SerialCloneable) sc.clone(); 
        
        
        System.out.println(sc_copy.num + ":" + (sc_copy.demo == sc.demo));
    }
    public static class SerialCloneable implements Cloneable, Serializable{

        private static final long serialVersionUID = 122348L;
        
        Integer num = null;
        Demo demo;
        @Override
        protected Object clone() throws CloneNotSupportedException {
//            return super.clone(); 这种方式的clone仅仅是浅拷贝
            // 利用序列化进行深拷贝
            try {
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                try (ObjectOutputStream out = new ObjectOutputStream(bao))
                {
                    out.writeObject(this);
                }
                
                // read a clone of the object from the byte array
                try (ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray())))
                {
                    return input.readObject();
                }
                
            } 
            catch(IOException | ClassNotFoundException e)
            {
                CloneNotSupportedException e2 = new CloneNotSupportedException();
                e2.initCause(e);
                throw e2;
            }
            
        }
        
        
    }
    
    public static class Demo implements Serializable{
        public String test = null;
    }
}
