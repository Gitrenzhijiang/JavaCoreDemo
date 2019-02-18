package com.test.unit1;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 从迭代到流的操作
 * @author renzhijiang
 *
 */
public class For2Stream {

    public static void main(String[] args) {
        // test1_2();
         test1_3();
    }
    // 将字符串的每一个字符当作一个独立的元素放入结果流中
    public static Stream<String> letters(String s){
        List<String> result = new ArrayList<>();
        for (int i = 0;i < s.length();i++) {
            result.add(s.substring(i, i+1));
        }
        return result.stream();
    }
    // 转换流
    public static void test1_3() {
        Stream<String> wordStream = Stream.of("abc", "def", "ghi", "网络");
        // Stream<Stream<String>> result = wordStream.map(w->letters(w));
        // System.out.println(result.count());
        long count = wordStream.flatMap(w->letters(w)).count();
        System.out.println(count); // flatMap拥有合并功能, 单子论
        // 连接流, 还有concat方法
        Optional<Double> minValue = Stream.iterate(1.0, p -> p*2)
                .distinct() //保证流中元素在流中独一无二
                .peek(e->System.out.println(e))
                .limit(20).min((d1, d2)->{return d1.compareTo(d2);});
        System.out.println(minValue.orElse(-1.0)); // 如果没有最小值返回默认值-1
    }
    /**
     * 生成流
     */
    public static void test1_2() {
        Stream<String> echos = Stream.generate(()->"你好!");
        System.out.println(echos.iterator().next() + "无限长度的流生成?:");
        Stream<Double> randoms = Stream.generate(Math::random);
        System.out.println(randoms.iterator().next());
        
        Stream<BigInteger> integers =
                Stream.iterate(BigInteger.ONE, n->n.add(BigInteger.TEN));
        integers.limit(5).forEach((data)->System.out.println(data));
        
        Stream<String> wordAnotherWay = Pattern.compile("\\PL+")
                .splitAsStream("abc myname  任志江   hello");
        wordAnotherWay.forEach((data)->{System.out.println(":"+data);});
        
        try {
            Stream<String> fileStream = Files
                .lines(Paths.get("D:\\myfile\\JavaSource\\w1\\JavaCoreDemo\\src\\com\\test\\unit1\\For2Stream.java"));
            fileStream.limit(2).forEach((line)->System.out.println(line));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void test1_1() {
        try {
            String contents = new String(
                    Files.readAllBytes(
                            Paths.get("D:\\myfile\\JavaSource\\w1\\JavaCoreDemo\\src\\com\\test\\unit1\\For2Stream.java")));
            // 这里的\PL+ 是以非单词分隔符分隔单词
            List<String> words = Arrays.asList(contents.split("\\PL+"));
            // 现在迭代它
            long count = 0;
            for (String w : words) {
                if (w.length() > 3) {
                    count++;
                }
            }
            System.out.println("count:" + count);
            // 使用流时, 相同的操作变成这样:
            count = words.stream().filter(w -> w.length() > 3).count();
            System.out.println("count:" + count);
            
            count = words.parallelStream().filter(w -> w.length() > 3).count();
            System.out.println("count:" + count);
            
            // 多种方式获得流
            Stream<String> wordStream = Stream.of(contents.split("\\PL+"));
            System.out.println("wordStream:" + wordStream.count());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
