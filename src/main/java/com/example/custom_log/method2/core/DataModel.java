package com.example.custom_log.method2.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public abstract class DataModel {
    private Map<String, Object> fields = new HashMap<>();
    private List<Record> history;
 
    public void setField(String fieldName, Object value) {
        Object oldValue = fields.get(fieldName);
        fields.put(fieldName, value);
 
        // Only log changes for fields that have a @ModifiableField annotation
        List<Field> modifiableFields = getModifiableFields();

        Field field = modifiableFields.get(0);

        // 根据注解获取名称
        ModifiableField annotation = field.getAnnotation(ModifiableField.class);
        String value1 = annotation.value();


        if (modifiableFields.stream().anyMatch(f -> f.getName().equalsIgnoreCase(fieldName))) {
            // Compare old and new values
            if (oldValue != null && !oldValue.equals(value) || oldValue == null && value != null) {
                // Add to history
                Record record = new Record(fieldName, oldValue, value);
                getHistory().add(record);
                // Publish event
                DataModificationEvent event = new DataModificationEvent(this, fieldName, oldValue, value);
                MyApplication.getInstance().getModificationListener().notifyListeners(event);
            }
        }
    }
 
    public Object getField(String fieldName) {
        return fields.get(fieldName);
    }
 
    public List<Record> getHistory() {
        if (history == null) {
            history = new ArrayList<>();
        }
        return history;
    }
 
    public List<Field> getModifiableFields() {

        // 获取父类的属性值
        // 获取父类
        Class<?> parentClass = getClass().getSuperclass();

        // 获取父类的所有属性
        Field[] parentFields = parentClass.getDeclaredFields();

        List<Field> result = new ArrayList<>();
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ModifiableField.class)) {
                result.add(field);
            }
        }
        return result;
    }
}