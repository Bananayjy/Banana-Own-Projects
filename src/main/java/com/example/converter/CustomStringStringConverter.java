package com.example.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/** 自定义转换器
 * @author banana
 * @create 2023-12-26 23:15
 */
//将自定义转换器基础EasyExcel提供的转换接口Converter
public class CustomStringStringConverter implements Converter<String> {
    //指定该转换器支持的 Java 类型，这里指定为 String.class
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    //指定该转换器支持的 Excel 数据类型，这里指定为 CellDataTypeEnum.STRING，表示支持读取 Excel 中的字符串类型数据
    /*
    以下是 CellDataTypeEnum 中定义的所有单元格类型：
    - BOOL：布尔类型
    - ERROR：错误类型
    - FORMULA：公式类型
    - INLINE_STR：内联字符串类型
    - NUMBER：数字类型
    - STRING：字符串类型
    - DATE：日期类型
    - EMPTY：空类型
    */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 将读取到的 Excel 数据转换为 Java 对象中的数据
     * 这里读的时候会调用
     *
     * @param context
     * @return
     */
    @Override
    public String convertToJavaData(ReadConverterContext<?> context) {
        return "自定义：" + context.getReadCellData().getStringValue();
    }

    /**
     * 用于将 Java 对象中的数据转换为写入 Excel 文件中的数据
     * 这里是写的时候会调用 不用管
     *
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) {
        return new WriteCellData<>(context.getValue());
    }

}
