package com.test.unit2;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;

/**
 * 2.5 操作文件
 * @author REN
 *
 */
public class OperatorFile {
    public static void main(String[] args) throws IOException {
//        testPath();
//        testFiles();
        // 内存映射文件
        Path path = Paths.get("D:", "123.txt");
        System.out.println(checksumBufferedInputStream(path));
        System.out.println("-----");
        System.out.println(checksumMappedFile(path));
        
    }
    /**
     * 普通缓冲流计算crc
     * @param filename
     * @return
     * @throws IOException
     */
    public static long checksumBufferedInputStream(Path filename) throws IOException {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(filename))){
            CRC32 crc = new CRC32();
            int c;
            while ((c = in.read()) != -1)
                crc.update(c);
            return crc.getValue();
        }
    }
    public static long checksumMappedFile(Path filename) throws IOException {
        try(FileChannel channel = FileChannel.open(filename)){
            CRC32 crc = new CRC32();
            int length = (int) channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);
            for (int p = 0; p < length;p++) {
                int c = buffer.get(p);
                crc.update(c);
            }
            return crc.getValue();
        }
    }
    
    public static void testPath() {
        Path base = Paths.get("D:", "myfile", "JavaSource", "w1", "JavaCoreDemo");
        Path workRelative = Paths.get("g.zip");
        System.out.println(workRelative.toFile().getAbsolutePath());
        
        Path workPath = base.resolve("nums.txt");
        System.out.println(workPath.toFile().getAbsolutePath());
    }
    public static void testFiles() throws IOException {
        Path base = Paths.get("D:", "myfile", "JavaSource", "w1", "JavaCoreDemo");
        Path filePath = base.resolve("nums.txt");
        byte[] bytes = Files.readAllBytes(filePath);
        
        Files.readAllLines(filePath).stream().forEach(e->System.out.println(e));
        
    }
}
