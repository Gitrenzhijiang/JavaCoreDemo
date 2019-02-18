package com.test.dynamic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 算法接口
 * @author REN
 */
public interface Fun {
    void fun();
}
class A {
    public void fun() {
        // 在D://根目录下面 创建文件1.txt
        System.out.println("fun fun fun");
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            String filename = "D://1.txt";
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            for (int i = 0; i < 10;i++) {
                bw.write("hello, i am " + i);
                bw.newLine();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}