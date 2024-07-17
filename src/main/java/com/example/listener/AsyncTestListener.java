package com.example.listener;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.mapper.DemoMapper;
import com.example.model.pojo.R;
import com.example.service.demoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Slf4j
@Component
public class AsyncTestListener implements AsyncListener,Runnable {

    @Resource
    private demoService demoService;

    boolean isComplete = true;
    int i = 1;

 
   /* private RedisTemplate<String, String> redisTemplate;*/

    private AsyncContext asyncContext;

    /*public AsyncTestListener(RedisTemplate<String, String> redisTemplate, AsyncContext asyncContext) {
        this.redisTemplate = redisTemplate;
        this.asyncContext = asyncContext;
    }*/

    public AsyncTestListener() {

    }

    public AsyncTestListener(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }
 
    @Override
    public void run() {
        try {
            while(isComplete){
                System.out.println(Thread.currentThread().getName() + ":hhhh:" + i++);
                Thread.sleep(300);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        log.info("结束了");
        isComplete = false;
        ServletResponse response = asyncEvent.getAsyncContext().getResponse();
        response.setContentType("text/plain");
        response.getWriter().write("123456");
        response.getWriter().flush();
        asyncEvent.getAsyncContext().complete();
    }
 
    @Override
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        log.info("超时了");
        /*ServletResponse response = asyncEvent.getAsyncContext().getResponse();
        // 设置响应内容类型为 JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 构造 JSON 数据
        String json = JSONObject.toJSONString(R.ok("hahahha"));

        // 获取输出流并写入 JSON 数据
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();

        // 一定要结束，不然应该会有默认方法，导致重复对输出流进行编写
        asyncEvent.getAsyncContext().complete();*/
    }
 
    @Override
    public void onError(AsyncEvent asyncEvent) throws IOException {
        log.info("发生错误了");
        /*ServletResponse response = asyncEvent.getAsyncContext().getResponse();
        response.setContentType("text/plain");
        response.getWriter().write("请求超时了，请稍后再试。");
        response.getWriter().flush();
        asyncEvent.getAsyncContext().complete();*/
    }
 
    @Override
    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        log.info("开始异步调用了");
    }
}