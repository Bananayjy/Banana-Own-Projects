package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author banana
 * @create 2023-12-05 20:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Demo extends Model<Demo> {
    @TableId(value = "id", type= IdType.ASSIGN_ID)
    private Integer id;

    private String name;

    private String sex;

    private String context;
}
