package com.example;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.model.pojo.WriteDemoData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * 写Excel相关测试
 * @author banana
 * @create 2024-04-14 16:59
 */
@SpringBootTest
@Slf4j
public class WriteExcelApplicationTests {


    /**
     * 1、最简单的写
     * 创建excel对应的实体对象 参照{@link WriteDemoData}
     * 创建ExcelWriter对象并输出对应的信息
     */
    @Test
    public void simpleWrite(){

        // 写法一
        // 获取文件输出目录
        String fileName = getExcelUrl("simpleWrite1_1.xlsx");
        // 指定通过WriteDemoData写对象去写，然后写到第一个名字为工作表1.1的sheet中
        EasyExcel.write(fileName, WriteDemoData.class)
                .sheet("工作表1.1")
                .doWrite(() -> {
                    // 使用lambda 表达式
                    // 作用：可在这里根据具体需求进行数据处理，比如对用户数据进行筛选、排序等操作
                    return data();
                });

        // 写法二
        fileName = getExcelUrl("simpleWrite1_2.xlsx");
        // 直接在dowrite方法中传递集合（适用于已经有完整数据集合的情况，不需要进行额外的数据处理）
        EasyExcel.write(fileName, WriteDemoData.class).sheet("工作表1.2").doWrite(data());

        // 写法三
        fileName = getExcelUrl("simpleWrite1_3.xlsx");
        // 创建了一个ExcelWriter对象
        ExcelWriter excelWriter = EasyExcel.write(fileName, WriteDemoData.class).build();
        // 创建了一个WriteSheet对象，用于指定要写入的Excel文件中的工作表（sheet）
        WriteSheet writeSheet = EasyExcel.writerSheet("工作表1.3").build();
        /**
         * write方法参数说明
         * 参数一： 需要写入的数据集
         * 参数二： 写入的目标Sheet
         */
        excelWriter.write(data(), writeSheet);

    }









    //通用数据生成
    private List<WriteDemoData> data() {
        List<WriteDemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            WriteDemoData data = new WriteDemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    //根据文件名称，获取Excel目录地址
    private String getExcelUrl(String docName){
        return this.getClass().getClassLoader().getResource("").getPath()  + docName;
    }
}
