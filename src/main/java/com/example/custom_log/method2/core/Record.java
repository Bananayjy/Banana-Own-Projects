package com.example.custom_log.method2.core;

import lombok.Data;

@Data
public class Record {
    private String fieldName;
    private Object oldValue;
    private Object newValue;
 
    public Record(String fieldName, Object oldValue, Object newValue) {
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
 
    // 省略getter和setter方法
}