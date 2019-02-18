package com.test.unit8;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.swing.JFrame;

/**
 * 这个实例演示了如何在JAVA GUI中使用脚本
 * @author REN
 *
 */
public class ScriptTest {
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            try {
                ScriptEngineManager manager = new ScriptEngineManager();
                String language;
                if (args.length == 0) {
                    System.out.println("Available factories: ");
                    for (ScriptEngineFactory factory : manager.getEngineFactories()) {
                        System.out.println(factory.getEngineName());
                    }
                    language = "nashorn";
                }else {
                    language = args[0];
                }
                
                final ScriptEngine engine = manager.getEngineByName(language);
                if (engine == null) {
                    System.out.println("No engine for " + language);
                    return;
                }
                final String framClassName = args.length < 2 ? "buttons1.ButtonFrame" : args[1];
                JFrame frame = (JFrame) Class.forName(framClassName).newInstance();
                // this inputStream is the stream of script`s file
                InputStream in = frame.getClass().getResourceAsStream("init." + language); 
                System.out.println(language);
                // 解释脚本
                engine.eval(new InputStreamReader(in));
                Map<String, Component> components = new HashMap<>();
                getComponentBindings(frame, components);
                // 把所有的<String,Component>键值对放入脚本的引擎域中
                components.forEach((name, c)->engine.put(name, c));
                
                final Properties events = new Properties();
                // now, the inputStream define the event handler
                in = frame.getClass().getResourceAsStream(language + ".properties");
                events.load(in);
                
                for (final Object e : events.keySet()) {
                    String[] s = ((String)e).split("\\.");
                    addListener(s[0], s[1], (String)events.get(e), engine, components);
                }
                frame.setTitle("ScriptTest");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    /**
     * Add a listener to an object whose listener method executes a script.
     * @param beanName 
     * @param eventName the name of the listener type, such as "action" or "change"
     * @param scriptCode
     * @param engine
     * @param components
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws IntrospectionException 
     */
    private static void addListener(String beanName, String eventName, final String scriptCode,
            ScriptEngine engine,
            Map<String, Component> components) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
        // 拿到需要注册监听的bean
        Object bean = components.get(beanName);
        
        // 拿到对应事件描述符
        EventSetDescriptor descriptor = getEventSetDescriptor(bean, eventName);
        
        if (descriptor == null)
        {
            return;
        }
        // 给bean 对象添加该事件侦听器  监听什么事件已经在前面拿事件描述符时定义好了
        descriptor.getAddListenerMethod().invoke(bean, 
                Proxy.newProxyInstance(null, new Class[] {descriptor.getListenerType()},
                        (proxy, method, args)->
                            {
                                System.out.println(beanName + "-->" + eventName);
                                Object o = engine.eval(scriptCode);
                                components.get(beanName).setBackground(Color.decode((String)o));
                                return null;
                            }
        ));
        
    }
    /**
     * 返回bean定义此bean触发的相应事件类型的事件描述符
     * @param bean
     * @param eventName [container
            [item
            [hierarchy
            [ancestor
            [change
            [container
            [item
            [hierarchy
            [ancestor
            [change
            [mouseMotion
            [focus
            [mouseWheel
            [hierarchyBounds
            [mouse
            [component
            [inputMethod
            [action
     * @return
     * @throws IntrospectionException
     */
    private static EventSetDescriptor getEventSetDescriptor(Object bean, String eventName) throws IntrospectionException {
        for (EventSetDescriptor descriptor : Introspector.getBeanInfo(bean.getClass())
                .getEventSetDescriptors())
        {
            if (descriptor.getName().equals(eventName))
                return descriptor;
        }
        return null;
    }
    /**
     * 将指定组件及其内部下所有组件 命名并放入容器中
     * @param c
     * @param namedComponents
     */
    private static void getComponentBindings(Component c, Map<String, Component> namedComponents) {
        String name = c.getName();
        if (name != null) {namedComponents.put(name, c);}
        // 考虑到这个组件可能是Container, 将它下面的所有组件 也命名放入容器
        if (c instanceof Container) {
            for (Component child : ((Container)c).getComponents())
            {
                getComponentBindings(child, namedComponents);
            }
        }
    }
}
