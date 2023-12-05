package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.Demo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author banana
 * @create 2023-12-05 20:30
 */
@Mapper
public interface DemoMapper extends BaseMapper<Demo> {

}
