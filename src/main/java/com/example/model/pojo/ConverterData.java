package com.example.model.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.example.converter.CustomStringStringConverter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/** 自定义格式转换对象
 * @author banana
 * @create 2023-12-26 23:13
 */
@Data
public class ConverterData {
    //我自定义 转换器，不管数据库传过来什么 。我给他加上“自定义：”
    @ExcelProperty(converter = CustomStringStringConverter.class)
    private String string;

    //这里用string 去接日期才能格式化。我想接收年月日格式
    @DateTimeFormat(style = "yyyy年MM月dd日HH时mm分ss秒")
    private String date;

    //我想接收百分比的数字
    @NumberFormat("#.##%")
    private String doubleData;
}
