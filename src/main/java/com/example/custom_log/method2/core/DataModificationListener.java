package com.example.custom_log.method2.core;

import org.springframework.stereotype.Component;
 
import java.util.ArrayList;
import java.util.List;
 
@Component
public class DataModificationListener {
    private List<ModificationLogger> loggers = new ArrayList<>();
 
    public void addLogger(ModificationLogger logger) {
        loggers.add(logger);
    }
 
    public void removeLogger(ModificationLogger logger) {
        loggers.remove(logger);
    }
 
    public void notifyListeners(DataModificationEvent event) {
        for (ModificationLogger logger : loggers) {
            logger.log(event.getDataModel(), new Record(event.getFieldName(), event.getOldValue(), event.getNewValue()));
        }
    }
}