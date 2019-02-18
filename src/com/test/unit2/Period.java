package com.test.unit2;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Date;

public final class Period implements Serializable{
    private static final long serialVersionUID = 189562L;
    private final Date start;
    private final Date end;
    
    public Period(Date start, Date end) {
        this.start = start;
        this.end = end;
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
    }
    public Date start() {return new Date(start.getTime());}
    
    public Date end() {return new Date(end.getTime());}
    
    public String toString() {return start + "-" + end;}
    // 这个函数将在对象被反序列化后执行
    private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
        input.defaultReadObject();
        if (start.compareTo(end) > 0) {
            throw new InvalidObjectException(start + " after " + end);
        }
    }
    protected Object readResolve()throws ObjectStreamException{
        System.out.println("resResolve");
        return this;
    }
    
}
