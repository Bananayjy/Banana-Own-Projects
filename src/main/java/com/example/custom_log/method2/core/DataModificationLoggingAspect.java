package com.example.custom_log.method2.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
@Aspect
@Component
public class DataModificationLoggingAspect {
    @Autowired
    private DataModificationListener modificationListener;
 
    @AfterReturning(
            value = "@annotation(com.example.custom_log.method2.core.ModifiableField)",
            returning = "returnValue"
    )
    public Object logModification(JoinPoint joinPoint, Object returnValue) {
        DataModel dataModel = (DataModel) returnValue;
        DataModel proxiedDataModel = (DataModel) DataModelProxy.create(dataModel);
        return proxiedDataModel;
    }

    @Before(value = "@annotation(com.example.custom_log.method2.core.ModifiableField)")
    public void logModifiable(JoinPoint joinPoint) {
        DataModel dataModel = (DataModel) joinPoint.getArgs()[0];
    }
}