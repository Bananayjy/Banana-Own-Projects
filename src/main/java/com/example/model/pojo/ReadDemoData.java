package com.example.model.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 读对象
 * @author banana
 * @create 2023-12-26 11:39
 */

@Data  //生成getter、setter、toString、equals、hashCode等方法
public class ReadDemoData {

    private String string;

    private Date date;

    private Double doubleData;
}