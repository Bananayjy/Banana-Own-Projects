package com.example.custom_log.method2.core;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public class MyApplication {

    private DataModificationListener modificationListener = new DataModificationListener();

    private static MyApplication instance = new MyApplication();

    @PostConstruct
    public void init() {
        modificationListener.addLogger(new ConsoleModificationLogger());
    }

    private MyApplication() {
        // 添加控制台日志记录器
        modificationListener.addLogger(new ConsoleModificationLogger());
    }
 
    public void registerModificationLogger(ModificationLogger logger) {
        modificationListener.addLogger(logger);
    }
 
    public void unregisterModificationLogger(ModificationLogger logger) {
        modificationListener.removeLogger(logger);
    }
 
    public DataModificationListener getModificationListener() {
        return modificationListener;
    }
 
    public static MyApplication getInstance() {
        return instance;
    }
}