package com.test.unit8;

import java.io.InputStreamReader;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScripEngineManagerTest {

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager(); 
        
        
     // 将键值对放入全局作用域, 任何绑定到manager的引擎都可以看到
        manager.put("global_scope", "global_scope"); 
        System.out.println(manager.getBindings().get("global_scope"));
//        for (ScriptEngineFactory sef : manager.getEngineFactories())
//        {
//            System.out.println("EngineName:"+sef.getEngineName());
//            System.out.println("LanguageName:" + sef.getLanguageName());
//            System.out.println(sef.getScriptEngine());
//            System.out.println("======");
//        }
        
        ScriptEngine se = manager.getEngineByName("nashorn");
        
     // java 向脚本传递参数, 这里的作用域是引擎作用域.
        se.put("person", new Person("renzhijiang", 20));
        System.out.println(se.getContext().getAttribute("person", ScriptContext.ENGINE_SCOPE));
        // 这里定义了一个js函数， 存储在文件中
       se.eval(new InputStreamReader(ScripEngineManagerTest
                .class.getClassLoader()
                .getResourceAsStream("js"))
                );
       
       Object ret = se.eval("person.age"); // 调用js函数...  test()
       System.out.println(ret.getClass().getName()); //java 将自动映射js的返回结果到ret
        System.out.println(ret);
    }
    public static class Person{
        private String name;
        private Integer age;
        
        public Person() {
        }
        public Person(String name, Integer age) {
            super();
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getAge() {
            return age;
        }
        public void setAge(Integer age) {
            this.age = age;
        }
        @Override
        public String toString() {
            return "Person [name=" + name + ", age=" + age + "]";
        }
    }
}
