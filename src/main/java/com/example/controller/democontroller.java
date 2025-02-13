package com.example.controller;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author banana
 * @create 2023-10-06 21:57
 */
@RestController
@RequestMapping("/demo")
public class democontroller {

    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        System.out.println(redisTemplate);
        redisTemplate.opsForValue().set("name2", "张三");
    }

}
