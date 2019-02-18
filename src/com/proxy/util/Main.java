package com.proxy.util;

public class Main {
    public static void main(String[] args) {
        Interceptor myinterceptor = new Interceptor() {
            
            @Override
            public void before(Object obj) {
                System.out.println("before");
            }
            
            @Override
            public void afterThrowing(Object obj) {
                System.out.println("afterThrowing");
            }
            
            @Override
            public void afterReturning(Object obj) {
                System.out.println("afterReturning");
            }
            
            @Override
            public void after(Object obj) {
                System.out.println("after");
            }
        };
        
        RoleService rs = new RoleServiceImpl();
        rs = ProxyBeanFactory.getBean(rs, myinterceptor);
        rs.print();
        rs.count();
    }
}
