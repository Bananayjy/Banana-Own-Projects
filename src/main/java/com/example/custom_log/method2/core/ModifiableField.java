package com.example.custom_log.method2.core;

import java.lang.annotation.*;
 
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModifiableField {

    String value();
}