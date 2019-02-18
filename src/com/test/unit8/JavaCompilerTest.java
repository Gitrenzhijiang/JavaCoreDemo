package com.test.unit8;

import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 测试 编译便捷之法
 * @author REN
 *
 */
public class JavaCompilerTest {

    public static void main(String[] args) {
        
        test_jcc();
        
    }
    public static void test_jcc() {
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
        
        
        // 需要编译的javafileObject
        StandardJavaFileManager fileManager = jc.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> fileObjects =  
                fileManager.getJavaFileObjectsFromStrings(
                        Arrays.asList("D:\\Test.java", "D:\\Word.java"));
        // DiagnosticCollector
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        // 
        
        Iterable<String> options = Arrays.asList("-g", "-d", "D:\\");       
        
        JavaCompiler.CompilationTask task = jc.getTask(null, fileManager, diagnostics, options, 
                null, fileObjects);
        boolean b = task.call();
        System.out.println(b);
        // 所有的诊断信息
        for (Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics()) {
            System.out.println(d.getKind() + ": " + d.getMessage(null));
        }
        
    }
    
}
