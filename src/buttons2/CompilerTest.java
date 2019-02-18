package buttons2;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import org.apache.jasper.EmbeddedServletOptions;

import javax.tools.ToolProvider;

import com.test.unit8.ByteArrayJavaClass;
import com.test.unit8.StringBuilderJavaSource;

public class CompilerTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        
        // JavaFileObject 集合=》需要编译的source集合
        final List<ByteArrayJavaClass> classFileObjects = new ArrayList<>();
        // 错误/报警消息收集器
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        
        JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        
        // 这里为什么需要 x.??? 因为没有文件?
        fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager) 
        {
            @Override
            public JavaFileObject getJavaFileForOutput(Location location, final String className,
                    Kind kind, FileObject sibling) throws IOException {
                if (className.startsWith("x."))
                {
                    ByteArrayJavaClass fileObject = new ByteArrayJavaClass(className);
                    classFileObjects.add(fileObject);
                    return fileObject;
                }else
                    return super.getJavaFileForOutput(location, className, kind, sibling);
            }
        };
        String frameClassName = args.length == 0 ? "buttons2.ButtonFrame" : args[0];
        JavaFileObject source = buildSource(frameClassName);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null,
                null, Arrays.asList(source));
        Boolean result = task.call(); // 实际编译这些source
        
        for (Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics()) {
            System.out.println(d.getKind() + ": " + d.getMessage(null));
        }
        fileManager.close();
        
        if (!result) {
            System.out.println("Compilation failed.");
            System.exit(1);
        }
        
        java.awt.EventQueue.invokeLater(()->
        {
            ClassLoader loader = null;
            try {
                Map<String, byte[]> byteCodeMap = new HashMap<>();
                for (ByteArrayJavaClass cl : classFileObjects) {
                    System.out.println(cl.getName());//   /x.Frame]]]
                    byteCodeMap.put(cl.getName().substring(1), cl.getBytes());
                    loader = new MapClassLoader(byteCodeMap);
                }
                JFrame frame = (JFrame) loader.loadClass("x.Frame").newInstance();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("CompilerTest");
                frame.setVisible(true);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    );
        
    }
    /**
     * Build the source for the subclass that implements the addEventHandlers method.
     * @param superclassName
     * @return
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    static JavaFileObject buildSource(String superclassName) throws ClassNotFoundException, IOException {
        StringBuilderJavaSource source = new StringBuilderJavaSource("x.Frame");
        source.append("package x;\n");
        source.append("public class Frame extends " + superclassName + " {");
        source.append("protected void addEventHandlers(){");
        final Properties props = new Properties();
        props.load(Class.forName(superclassName).getResourceAsStream("action.properties"));
        for (Map.Entry<Object, Object> e : props.entrySet()) {
            String key = (String)e.getKey();
            String eventCodes = (String) e.getValue();
            String[] keyAfterSplit = key.split("./");
            String beanName = keyAfterSplit[0];
            String eventName = keyAfterSplit[1];
            String s_eventName = eventName.substring(0, 1).toUpperCase()
                    + eventName.substring(1);
            String eventClassName = packageName + s_eventName  + "Listener";
            source.append(beanName + ".add" + s_eventName +
                      "Listener(new " + eventClassName + "(){");
            // we should resolve the eventCodes
            Map<String, String> emtoCode = new HashMap<>(1);
            String[] eandcs = eventCodes.split("#");
            for (String eandc:eandcs) {
                String[] ts = eandc.split(":");
                if (ts.length == 2) 
                    emtoCode.put(ts[0], ts[1]);
                else
                    emtoCode.put(ts[0], "");
            }
            source.append(getAllMethodOfInterface(eventClassName, emtoCode).toString());
            source.append("} );");
        }
        source.append("} }");
        return source;
    }
    
    static StringBuilder  getAllMethodOfInterface(String listener, Map<String, String> e2cMap) throws ClassNotFoundException {
        
        String className = listener;
        Class clazz = Class.forName(className);
        if (clazz == null) {
            throw new RuntimeException("class not found:" + className);
        }
        StringBuilder sb = new StringBuilder();
        for (Method m : clazz.getDeclaredMethods()) {
            StringBuilder temp = new StringBuilder();
            String methodName = m.getName();
            String paraClassName = m.getParameterTypes()[0].getName();
            String returnTypeName = m.getReturnType().getName();
            temp.append("public " + returnTypeName + " " + methodName + "(" 
                    + paraClassName + " e){");
            temp.append(e2cMap.getOrDefault(methodName, ""));
            temp.append("}\n");
            sb.append(temp.toString());
        }
        return sb;
    }
    static final String packageName = "java.awt.event.";
}
