package com.test.dynamic;

import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaCompilerUtils {
    public static void compile(List<String> filePaths, String outPutDir) {
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
        System.out.println("jc=" + jc);
        // 需要编译的javafileObject
        StandardJavaFileManager fileManager = jc.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> fileObjects =  
                fileManager.getJavaFileObjectsFromStrings(
                        filePaths);
        // DiagnosticCollector
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        // 
        
        Iterable<String> options = Arrays.asList("-g", "-d", outPutDir);       
        
        JavaCompiler.CompilationTask task = jc.getTask(null, fileManager, diagnostics, options, 
                null, fileObjects);
        boolean b = task.call();
        // 所有的诊断信息
        for (Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics()) {
            System.out.println(d.getKind() + ": " + d.getMessage(null));
        }
        
    }
}
