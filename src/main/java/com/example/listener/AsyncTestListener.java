package com.example.listener;

import com.example.mapper.DemoMapper;
import com.example.service.demoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import java.io.IOException;
 
 
@Slf4j
@Component
public class AsyncTestListener implements AsyncListener,Runnable {

    @Resource
    private demoService demoService;

    boolean isComplete;
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
            while(true){
                if(isComplete){
                    log.info("已经退出");
                    break;
                }
                System.out.println("guagua" + i ++);
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(300);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        log.info("结束了");
        isComplete = true;
 
    }
 
    @Override
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        log.info("超时了");
        ServletResponse response = asyncEvent.getAsyncContext().getResponse();
        response.setContentType("text/plain");
        response.getWriter().write("请求超时了，请稍后再试。");
        response.getWriter().flush();
        asyncEvent.getAsyncContext().complete();
    }
 
    @Override
    public void onError(AsyncEvent asyncEvent) throws IOException {
        log.info("超时了");
        ServletResponse response = asyncEvent.getAsyncContext().getResponse();
        response.setContentType("text/plain");
        response.getWriter().write("请求超时了，请稍后再试。");
        response.getWriter().flush();
        asyncEvent.getAsyncContext().complete();
    }
 
    @Override
    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        log.info("超时了");
    }
}