package com.example.custom_log.method2.core;

public class DataModificationEvent {
    private DataModel dataModel;
    private String fieldName;
    private Object oldValue;
    private Object newValue;
 
    public DataModificationEvent(DataModel dataModel, String fieldName, Object oldValue, Object newValue) {
        this.dataModel = dataModel;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
 
    public DataModel getDataModel() {
        return dataModel;
    }
 
    public String getFieldName() {
        return fieldName;
    }
 
    public Object getOldValue() {
        return oldValue;
    }
 
    public Object getNewValue() {
        return newValue;
    }
}