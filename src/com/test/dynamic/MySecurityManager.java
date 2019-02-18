package com.test.dynamic;

import java.security.Permission;

public class MySecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
//        System.out.println(perm.getName() + "-----" + perm.getActions());
//        if ("sun.tools.ToolProvider".equals(perm.getName()) || 
//                "java.home".equals(perm.getName())) {
//            
//            return;
//        }
        super.checkPermission(perm);
    }
}
