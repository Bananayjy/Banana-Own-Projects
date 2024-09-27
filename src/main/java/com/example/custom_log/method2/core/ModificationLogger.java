package com.example.custom_log.method2.core;

import java.util.List;
 
public interface ModificationLogger {
    void log(DataModel dataModel, Record record);
 
    List<Record> getHistory(DataModel dataModel);
}