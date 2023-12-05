package com.example.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.DemoMapper;
import com.example.model.entity.Demo;
import com.example.service.demoService;
import org.springframework.stereotype.Service;

/**
 * @author banana
 * @create 2023-12-05 18:52
 */
@Service
public class demoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements demoService {


}
