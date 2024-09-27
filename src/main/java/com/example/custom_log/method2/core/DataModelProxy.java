package com.example.custom_log.method2.core;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
 
import java.lang.reflect.Method;
 
public class DataModelProxy implements MethodInterceptor {
    private final Object target;
 
    private DataModelProxy(Object target) {
        this.target = target;
    }
 
    public static Object create(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new DataModelProxy(target));
        return enhancer.create();
    }
 
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = method.invoke(target, args);
        return result;
    }
}