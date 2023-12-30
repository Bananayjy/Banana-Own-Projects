package com.example.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.example.model.pojo.CellDataReadDemoData;
import com.example.model.pojo.ConverterData;
import com.example.model.pojo.ReadDemoData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 读取公式和单元格类型监听器
 * @author banana
 * @create 2023-12-28 22:08
 */
@Slf4j
public class CellDataDemoHeadDataListener implements ReadListener<CellDataReadDemoData> {

    //每隔100条存储数据库，然后清理list，方便内存的回收
    private static final int BATCH_COUNT = 100;

    //缓存数据
    private List<CellDataReadDemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);


    //在解析 Excel 过程中发生异常时调用的方法。可以在该方法中记录日志或者进行异常处理等操作
    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {

    }



    //在读取 Excel 文件表头时调用的方法。可用于对表头进行校验或者记录日志等操作
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        // 如果想转成成 Map<Integer,String>
        // 方案1： 不要implements ReadListener 而是 extends AnalysisEventListener
        // 方案2： 调用 ConverterUtils.convertToStringMap(headMap, context) 自动会转换
    }

    //在读取到一条数据时调用的方法。T 表示读取到的数据类型。可以在该方法中对读取到的数据进行处理或者记录日志等操作
    @Override
    public void invoke(CellDataReadDemoData readDemoData, AnalysisContext analysisContext) {
        log.info("解析到一条数据：{}", JSON.toJSONString(readDemoData));
        cachedDataList.add(readDemoData);
        //达到BATCH_COUNT了,清空缓存，并可以去做一些处理（如存储一次数据库）
        //目的：防止几万条数据在内存中，容易OOM
        if(cachedDataList.size() >= BATCH_COUNT)
        {
            //一些业务操作（如存储数据库）
            //……
            saveData();

            //清除缓存
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    //在读取 Excel 文件中除数据外的其他内容时调用的方法。例如，批注、超链接等。可以在该方法中进行相应的处理
    @Override
    public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {

    }

    //在读取数据完成后调用的方法。可以在该方法中进行一些资源清理工作或者记录日志等操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //一些业务操作（如存储数据库,简单打印剩余数据）
        cachedDataList.stream().forEach(System.out::println);

        log.info("所有数据解析完成!");
    }

    //判断是否还有下一条数据需要读取。如果返回 true，会自动调用 invoke(T data, AnalysisContext analysisContext) 方法来读取下一条数据；
    // 如果返回 false，则结束读取数据的过程。
    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return true;
    }

    //模拟数据存储(这里就是简单的打印一下)
    private void saveData(){
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        cachedDataList.stream().forEach(System.out::println);
        log.info("存储数据库成功！");
    }
}
