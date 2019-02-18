package com.test.unit2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipTest {
    public static final String WORD_DIR = System.getProperty("user.dir");
    public static void main(String[] args) throws IOException {
        ZipFile zf = new ZipFile(WORD_DIR + File.separator + "g.zip");
        List<ZipEntry> lists = zf.stream().peek(e->System.out.println(e.getName())).collect(Collectors.toList());
        for (int i = 0;i < lists.size();i++){
            InputStream  is = zf.getInputStream(lists.get(i));
            System.out.println(is.available());
        }
        
        
    }
    
    
    

}
