package com.test.unit1;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Collecting {

    public static void main(String[] args) {
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<Locale>> countryToLocaleSet = locales
                .collect(Collectors.groupingBy(Locale::getCountry, 
                        Collectors.toSet()));
        System.out.println(countryToLocaleSet.keySet().size());
        
        
        IntStream is1 = IntStream.generate(() -> (int)(Math.random() * 100));
        show("is1", is1);
        IntStream is2 = IntStream.range(5, 10);
        show("is2", is2);
        IntStream is3 = IntStream.rangeClosed(5, 10);
        show("is3", is3);
    }
    /**
     * 将stream中的数据显示出来
     * @param title
     * @param stream
     */
    private static void show(String title, IntStream stream) {
        final int SIZE = 10;
        int[]  firstElements = stream.limit(SIZE + 1).toArray();
        System.out.print(title + ": ");
        for (int i = 0;i < firstElements.length;i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            if (i < SIZE) {
                System.out.print(firstElements[i]);
            }else {
                System.out.print("...");
            }
        }
        System.out.println();
    }
    public static class City{
        private String name;
        private String state;
        private int popuation;
        public City(String name, String state, int popuation) {
            super();
            this.name = name;
            this.state = state;
            this.popuation = popuation;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getState() {
            return state;
        }
        public void setState(String state) {
            this.state = state;
        }
        public int getPopuation() {
            return popuation;
        }
        public void setPopuation(int popuation) {
            this.popuation = popuation;
        }
        @Override
        public String toString() {
            return "City [name=" + name + ", state=" + state + ", popuation=" + popuation + "]";
        }
        
    }
}
