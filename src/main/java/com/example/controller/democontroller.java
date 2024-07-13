package com.example.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.listener.AsyncTestListener;
import com.example.model.pojo.R;
import com.example.service.demoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    // AsyncContext使用的测试
    @GetMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response){
        System.out.println("开始时的线程：" + Thread.currentThread().getName());

        // 创建AsyncContext
        AsyncContext asyncContext = request.startAsync(request, response);

        // 设置处理超时时间8s
        asyncContext.setTimeout(1000L);

        // AsyncTestListener asyncTestListener = new AsyncTestListener(asyncContext);

        asyncContext.addListener(asyncTestListener);

        asyncContext.start(asyncTestListener);

        System.out.println("结束时的线程：" + Thread.currentThread().getName());

        // 重复对响应流进行操作，会报错
        //return R.ok("ok了家人们");
    }

    // 通过输出流手动自定义响应json格式测试
    @GetMapping("/customJsonResponse")
    public void customJsonResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置响应内容类型为 JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 构造 JSON 数据
        String json = JSONObject.toJSONString(R.ok("hahahha"));

        // 获取输出流并写入 JSON 数据
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();

    }


}
