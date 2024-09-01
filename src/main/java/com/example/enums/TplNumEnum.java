package com.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author banana
 * @create 2024-09-01 15:16
 */
@Getter
@AllArgsConstructor
public enum TplNumEnum {

    Custom(0, "自定义模板"),
    KT(1, "金唐模板");

    private Integer val;

    private String desc;

    /**
     * 根据val获取对应的枚举类
     * @param val val值
     * @return 对应的枚举类，如果为匹配返回null
     */
    public static TplNumEnum getTplNumEnumByVal(Integer val) {
        TplNumEnum[] values = values();
        for (TplNumEnum value : values) {
            if(value.getVal().equals(val)) {
                return value;
            }
        }
        return null;
    }

}
