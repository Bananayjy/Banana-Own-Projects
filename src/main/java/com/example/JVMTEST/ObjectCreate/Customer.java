package com.example.JVMTEST.ObjectCreate;

/**
 * @author banana
 * @create 2025-01-20 22:49
 */
public class Customer {

    // 显示赋值
    int id = 1001;
    String name;
    Account acct;

    // 代码中初始化
    {
        name = "匿名客户";
    }

    // 构造器中初始化
    public Customer() {
        acct = new Account();
    }
}

class Account {

}

