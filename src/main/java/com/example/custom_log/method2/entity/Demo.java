package com.example.custom_log.method2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.custom_log.method2.core.DataModel;
import com.example.custom_log.method2.core.ModifiableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode(callSuper = true)
public class Demo extends DataModel {
    @TableId(value = "id", type= IdType.ASSIGN_ID)
    private Integer id;

    @ModifiableField("姓名")
    private String name;

    private String sex;

    private String context;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}

