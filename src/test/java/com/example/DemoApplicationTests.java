package com.example;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.example.listener.ConverterDataListener;
import com.example.listener.DemoHeadDataListener;
import com.example.listener.IndexOrNameDataListener;
import com.example.listener.ReadDemoDataListener;
import com.example.model.pojo.ConverterData;
import com.example.model.pojo.IndexOrNameData;
import com.example.model.pojo.ReadDemoData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
@Slf4j
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }


    /**
     * 最简单的读
     * 1、创建excel对应的实体对象（ReadDemoData）
     * 2、由于默认一行行地读取excel，所以需要创建excel，一行一行的回调监听器（ReadDemoDataListener）
     * 3、直接读即可
     */
    @Test
    public void simpleRead(){
        //写法一
        String fileName =  getExcelUrl("readExcel.xlsx");
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(fileName, ReadDemoData.class, new PageReadListener<ReadDemoData>(dataList ->{
            for(ReadDemoData demoData : dataList){
                log.info("读取到一条数据{}", JSON.toJSONString(demoData));
            }
        })).sheet().doRead();


        //写法二
        fileName = getExcelUrl("readExcel.xlsx");
        EasyExcel.read(fileName, ReadDemoData.class, new ReadListener<ReadDemoData>() {
            //单次缓存数据量
            public static final int BATCH_COUNT = 100;

            //临时存储
            private List<ReadDemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(ReadDemoData readDemoData, AnalysisContext analysisContext) {
                cachedDataList.add(readDemoData);
                if(cachedDataList.size() >= BATCH_COUNT){
                    saveData();
                    //存储完成，清理list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                saveData();
            }

            //模拟数据存储(这里就是简单的打印一下)
            private void saveData(){
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                cachedDataList.stream().forEach(System.out::println);
                log.info("存储数据库成功！");
            }
        }).sheet().doRead();


        //写法三
        fileName = getExcelUrl("readExcel.xlsx");
        //有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ReadDemoData.class, new ReadDemoDataListener()).sheet().doRead();

        //写法四
        fileName = getExcelUrl("readExcel.xlsx");
        // 一个文件一个reader
        try (ExcelReader excelReader = EasyExcel.read(fileName, ReadDemoData.class, new ReadDemoDataListener()).build()) {
            // 构建一个sheet 这里可以指定名字或者no
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            // 读取一个sheet
            excelReader.read(readSheet);
        }


    }


    /**
     * 指定列的下标或者列名
     *
     * <p>1. 创建excel对应的实体对象,并使用 ExcelProperty注解. 参照IndexOrNameData
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照 IndexOrNameDataListener
     * <p>3. 直接读即可
     */
    @Test
    public void indexOrNameRead() {
        String fileName = getExcelUrl("readExcel.xlsx");
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, IndexOrNameData.class, new IndexOrNameDataListener()).sheet().doRead();
    }


    /**
     * 3 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * <p>
     * 1. 创建excel对应的实体对象 参照ReadDemoData
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照ReadDemoDataListener
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void repeateRead(){


        //写法一：读取全部sheet
        //获取Excel表的路径
        String fileName = getExcelUrl("MultipleSheetExcel.xlsx");
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。
        // 然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(fileName, ReadDemoData.class, new ReadDemoDataListener()).doReadAll();


        //写法二：分开读取各个Sheet的信息
        fileName = getExcelUrl("MultipleSheetExcel.xlsx");
        try (ExcelReader excelReader = EasyExcel.read(fileName).build()) {
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(0).head(ReadDemoData.class).registerReadListener(new ReadDemoDataListener()).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(1).head(ReadDemoData.class).registerReadListener(new ReadDemoDataListener()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        }


    }


    /**
     * 4、日期、数字或者自定义格式转换
     * <p>
     * 默认读的转换器DefaultConverterLoader、loadDefaultReadConverter()
     * <p>1. 创建excel对应的实体对象 参照ConverterData.里面可以使用注解DateTimeFormat、NumberFormat或者自定义注解
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照ConverterDataListener
     * <p>3. 直接读即可
     */
    @Test
    public void converterRead() {
        String fileName = getExcelUrl("readExcel.xlsx");
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ConverterData.class, new ConverterDataListener())
                // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
                // .registerConverter(new CustomStringStringConverter())
                // 读取sheet
                .sheet().doRead();
    }


    /**
     * 5、多行头
     *
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 设置headRowNumber参数，然后读。 这里要注意headRowNumber如果不指定， 会根据你传入的class的{@link ExcelProperty#value()}里面的表头的数量来决定行数，
     * 如果不传入class则默认为1.当然你指定了headRowNumber不管是否传入class都是以你传入的为准。
     */
    @Test
    public void complexHeaderRead() {
        String fileName = getExcelUrl("readExcel.xlsx");
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ReadDemoData.class, new ReadDemoDataListener()).sheet()
                // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据DemoData 来解析，他没有指定头，也就是默认1行
                .headRowNumber(0).doRead();
    }




    /**
     * 7、读取表头数据
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoHeadDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void headerRead() {
        String fileName = getExcelUrl("readExcel.xlsx");
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ReadDemoData.class, new DemoHeadDataListener()).sheet().doRead();
    }

    //根据文件名称，获取Excel目录地址
    private String getExcelUrl(String docName){
        return this.getClass().getClassLoader().getResource("").getPath()  + docName;
    }


}
