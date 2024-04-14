package com.example.model.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 写对象
 * @author banana
 * @create 2024-04-14 17:15
 */

@Data
public class WriteDemoData {

    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    private Date date;

    @ExcelProperty("数字标题")
    private Double doubleData;

    // 如果有不需要生成标题的字段，使用@ExcelIgnore注解
    @ExcelIgnore
    private String ignore;
}
