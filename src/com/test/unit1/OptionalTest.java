package com.test.unit1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OptionalTest {

    public static void main(String[] args) {
        // 当option的值为null时， 返回的是empty对象。
        Optional<String> optionalString = Optional.ofNullable(null);
        System.out.println(optionalString.equals(Optional.empty()));

        optionalString = Optional.ofNullable("a");
        optionalString.ifPresent((e)->{System.out.println(e);});
        optionalString.ifPresent(e->System.out.println(e));
        List<String> list = Arrays.asList("1", "2", "3");
        optionalString.ifPresent(e->{list.set(0, "1a1s");System.out.println("mYtest:"+list.get(0));});
        list.stream().filter(e->e.length()>2).forEach(e->System.out.println(e));

        System.out.println(Optional.ofNullable(null).orElseGet(()->"okok"));// 返回给定的值，如果给定的值为Null， 返回okok
        
        // flatMap 在这里可以看成: 将一个Optional 看成一个大小为0/1的流， 它的作用是摊分流到流
        Optional<Double> result = inverse(4.0).flatMap(e->squareRoot(e));
        System.out.println(result.orElse(-1.0));
        // r2 初始的list ；这里证明了collect方法返回的是一个新的list
        List<String> r2 = new ArrayList<>();
        r2.add("1.1");r2.add("2.2");r2.add("3.3");
        System.out.println(r2.stream().collect(Collectors.joining(", ")));
        // 产生person的流
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("1", "任志江"));
        persons.add(new Person("2", "小听粉"));
        persons.add(new Person("3", "Covey0"));
        // 从流中收集一个map,map的key是 id, value是 person自身
        Map<String, Person> idToName = persons.parallelStream()
                .collect(Collectors.toMap(Person::getId, Function.identity()));
        for (Iterator iterator = idToName.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            System.out.println(idToName.get(key));
        }
    }
    private static Optional<Double> inverse(Double x){
        return x == null?Optional.empty():Optional.of(1 / x);
    }
    private static Optional<Double> squareRoot(Double x){
        return x < 0 ? Optional.empty():Optional.of(Math.sqrt(x));
    }
    
    private static class Person{
        public Person(String id, String name) {
            this.id = id;
            this.name = name;
        }
        private String id;
        private String name;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return "Person [id=" + id + ", name=" + name + "]";
        }
    }
}
