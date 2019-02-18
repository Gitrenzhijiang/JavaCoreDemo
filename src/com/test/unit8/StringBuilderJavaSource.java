package com.test.unit8;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class StringBuilderJavaSource extends SimpleJavaFileObject {
    private StringBuilder code;
    
    public StringBuilderJavaSource(String name) {
        super(URI.create("String:///" + name.replace(".", "/") + Kind.SOURCE.extension),
                Kind.SOURCE);
        code = new StringBuilder();
    }
    
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
    
    public void append(String str) {
        code.append(str);
        code.append('\n');
    }
}
