package com.test.unit8;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
/**
 * java 调用脚本的函数，或者脚本实现的实例
 * @author REN
 *
 */
public class InvocableTest {
    public static void main(String[] args) {
//        test_invokeFunction();
//        
        test_invokeMethod();
        
        test_js_java();
    }
    // 调用 js 的greet方法
    public static void test_invokeFunction() {
       
        Object ret = null;
        try {
            se.eval("function greet(how, whom){return how + ', ' + whom + '!'}");
            ret = ((Invocable)se).invokeFunction("greet", "Hello", "World");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(ret);
    }
    // 调用js类的方法
    public static void test_invokeMethod() {
        Object ret = null;
        try {
            // Define Greeter class in JavaScript
            se.eval("function Greeter(how){this.how = how}");
            // 给它定义方法
            se.eval("Greeter.prototype.welcome = function (whom){return this.how + ', ' + whom + '!!!'}");
            Object yo = se.eval("new Greeter('YO')");
            
            ret = ((Invocable)se).invokeMethod(yo, "welcome", "world");
            System.out.println(ret);
            
            // invoke by java
            Greeter g = ((Invocable)se).getInterface(yo, Greeter.class);
            System.out.println(g.welcome("js"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static void test_js_java() {
        Object ret = null;
        try {
            // Define a method in js
            se.eval("function welcome(whom){return 'Hello, ' + whom + '!'}");
            Greeter g = ((Invocable)se).getInterface(Greeter.class);
            ret = g.welcome("World");
            System.out.println(ret);
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    private static ScriptEngineManager manager = new ScriptEngineManager();
    
    private static  ScriptEngine se = manager.getEngineByName("js");
}

