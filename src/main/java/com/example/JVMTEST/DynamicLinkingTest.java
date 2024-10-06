package com.example.JVMTEST;

/**
 * @author banana
 * @create 2024-06-30 16:55
 */
public class DynamicLinkingTest {

    int num = 1;

    public void methodA() {
        System.out.println("methodA方法被调用……");
    }

    public void methodB() {
        System.out.println("MethodB方法被调用");
        methodA();

        num ++;
    }
}
