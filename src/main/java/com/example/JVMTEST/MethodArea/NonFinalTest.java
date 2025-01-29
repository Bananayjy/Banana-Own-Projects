package com.example.JVMTEST.MethodArea;

import org.springframework.core.annotation.Order;

/**
 * @author banana
 * @create 2025-01-08 23:29
 */
public class NonFinalTest {

    public static void main(String[] args) {
        A.method();
        System.out.println(A.count);
    }

}

class A {

    public static int count = 1;
    public static final int count2 = 2;

    public static void method() {
        System.out.println("baga");
    }

}
