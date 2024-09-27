package com.example.custom_log.method2.core;

import java.util.List;

public class ConsoleModificationLogger implements ModificationLogger {
    @Override
    public void log(DataModel dataModel, Record record) {
        System.out.printf("Entity \"%s\" [Field \"%s : %s  %s\n", dataModel.getClass().getSimpleName(), record.getFieldName(), record.getOldValue(), record.getNewValue());
    }
 
    @Override
    public List<Record> getHistory(DataModel dataModel) {
        return dataModel.getHistory();
    }
}