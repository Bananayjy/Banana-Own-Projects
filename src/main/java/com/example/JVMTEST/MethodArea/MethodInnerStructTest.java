package com.example.JVMTEST.MethodArea;

import java.io.Serializable;

/**
 * @author banana
 * @create 2025-01-07 22:05
 */
public class MethodInnerStructTest extends Object implements Serializable {

    // 属性
    public int num = 10;
    private static String str = "测试";

    // 空参构造器

    // 方法
    public void test1() {
        int count = 20;
        System.out.println("count=" + count);
    }

    // 静态方法
    public static int test2(int cal) {
        int result = 0;
        try {
            int value = 30;
            result = value / cal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("guagua");
        return super.equals(obj);
    }
}
