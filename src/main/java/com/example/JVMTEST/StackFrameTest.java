package com.example.JVMTEST;

/**
 * @author banana
 * @create 2024-06-27 0:03
 */
public class StackFrameTest {

    public static void main(String[] args) {
        StackFrameTest test = new StackFrameTest();
        test.method1();
    }

    public void method1() {
        System.out.println("method1方法开始执行");
        method2();
        System.out.println("method1方法执行结束");
    }

    public int method2() {
        System.out.println("method2方法开始执行");
        double v = method3();
        System.out.println("method2方法执行结束");
        return (int)v * 2;
    }

    public double method3() {
        System.out.println("method3方法开始执行");
        double num = 2.1;
        System.out.println("method3方法执行结束");
        return num;
    }

}
