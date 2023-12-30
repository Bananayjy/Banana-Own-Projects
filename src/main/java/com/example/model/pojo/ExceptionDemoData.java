package com.example.model.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 数据转换等异常处理对象
 * @author banana
 * @create 2023-12-30 17:52
 */
@Data
public class ExceptionDemoData {
    /**
     * 用日期去接字符串 肯定报错
     */
    private Date date;
}
