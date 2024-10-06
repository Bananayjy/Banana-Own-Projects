package com.example.JVMTEST;

import java.util.Date;

/**
 * @author banana
 * @create 2024-06-27 21:32
 */
public class LocalVariablesTest {

    /*public static void main(String[] args) {
        LocalVariablesTest localVariablesTest = new LocalVariablesTest();
        int num = 10;
    }*/

    private int count;

    // 构造方法
    public LocalVariablesTest(){
        this.count = 1;
    }

    public void test1() {
        Date date = new Date();
        String name = "name";
        test2(date, name);

    }

    public String test2(Date date, String name) {
        date = null;
        name = "123";
        double db = 2.0;
        char gender = '男';
        return name + "33";
    }

    public void test4() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
            // b的作用域到此结束
        }
        int c = a + 1;
    }

    public void test5() {
        int a = 1;
        System.out.println("hello");
    }



}
