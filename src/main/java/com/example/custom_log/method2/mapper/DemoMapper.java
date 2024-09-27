package com.example.custom_log.method2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.custom_log.method2.entity.Demo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoMapper extends BaseMapper<Demo> {

}