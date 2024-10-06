package com.example.JVMTEST;

/**
 * @author banana
 * @create 2024-06-11 22:45
 */
public class StringTest {

    public static void main(String[] args) {

        // 调用java.lang.String的默认构造器，创建java.lang.String的实例
        String str = new String();
        System.out.println(str.getClass().getClassLoader());

        StringTest stringTest = new StringTest();
        System.out.println(stringTest.getClass().getClassLoader());
    }
}
