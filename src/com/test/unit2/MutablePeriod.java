package com.test.unit2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MutablePeriod {

    public static void main(String[] args) {
        MutablePeriod mp = new MutablePeriod();
        Period p = mp.period;
        Date pEnd = mp.end;
        pEnd.setYear(78);
        System.out.println(p);
        
        pEnd.setYear(69);
        System.out.println(p);
    }
    // A period instance
    public final Period period;
    
    // period`s start field, to which we shouldn`t have access
    public final Date start;
    
    // period`s end field, to which we shouldn`t have access
    public final Date end;
    public MutablePeriod() {
        try {
           ByteArrayOutputStream bos = 
                   new ByteArrayOutputStream();
           ObjectOutputStream out = 
                   new ObjectOutputStream(bos);
           
           // Serialize a valid Period instance
           out.writeObject(new Period(new Date(), new Date()));
           
           byte[] ref = { 0x71, 0, 0x7e, 0, 5 };
           bos.write(ref); // the start field
           ref[4] = 4;     // Ref # 4
           bos.write(ref); // the end field
           
           // Deserialize Period and "stolen" Date references
           ObjectInputStream in = new ObjectInputStream(
                   new ByteArrayInputStream(bos.toByteArray()));
           System.out.println("...");
           period = (Period) in.readObject();
           start = (Date) in.readObject();
           end = (Date) in.readObject();
           
        }catch(Exception e) {
            throw new AssertionError(e);
        }
    }
}
