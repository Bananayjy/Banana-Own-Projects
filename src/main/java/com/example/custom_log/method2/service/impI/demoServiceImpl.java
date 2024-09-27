package com.example.custom_log.method2.service.impI;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.custom_log.method2.entity.Demo;
import com.example.custom_log.method2.mapper.DemoMapper;
import com.example.custom_log.method2.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class demoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements DemoService {


}