package com.example.controller;


import com.example.listener.AsyncTestListener;
import com.example.service.demoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author banana
 * @create 2023-10-06 21:57
 */
@RestController
@RequestMapping("/demo")
public class democontroller {

    @Resource
    private demoService demoService;

    @Resource
    private AsyncTestListener asyncTestListener;

    @GetMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response){
        System.out.println(Thread.currentThread().getName());

        // 创建AsyncContext
        AsyncContext asyncContext = request.startAsync(request, response);

        // 设置处理超时时间8s
        asyncContext.setTimeout(1000L);

        // AsyncTestListener asyncTestListener = new AsyncTestListener(asyncContext);

        asyncContext.addListener(asyncTestListener);

        asyncContext.start(asyncTestListener);

    }

}
