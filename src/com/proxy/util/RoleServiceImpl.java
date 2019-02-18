package com.proxy.util;

public class RoleServiceImpl implements RoleService{
    public void print() {
        System.out.println("roleService: print() ");
        throw new RuntimeException("ok");
    }
    public void count() {
        System.out.println("roleService: count() ");
    }
}
