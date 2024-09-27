package com.example.custom_log.method2.controller;

import com.example.custom_log.method2.core.DataModelProxy;
import com.example.custom_log.method2.core.DataModificationListener;
import com.example.custom_log.method2.core.MyApplication;
import com.example.custom_log.method2.entity.Demo;
import com.example.custom_log.method2.service.DemoService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author banana
 * @create 2024-09-25 15:33
 */
@RestController

public class DemoController {

    @Resource
    private DemoService demoService;

    @GetMapping("/test")
    public void test() {

        /*Demo demo = new Demo();
        demo.setId(10);
        demo.setName("yjy123");
        demo.setContext("111122223333");
        demo.setSex("男");
        demoService.save(demo);*/

        Demo demo = new Demo();
        demo.setId(10);
        // demo.setName("yjy123");
        demo.setField("name", "yjy123");
        demo.setContext("111122223333");
        demo.setSex("男");
        demoService.save(demo);
        
        /*Demo byId = demoService.getById(1);
        Demo demo1 = (Demo)DataModelProxy.create(byId);
        demo1.setName("yjy");
        demoService.updateById(demo1);*/




    }

    @PostConstruct
    public void init() {
        // test();
    }


}
