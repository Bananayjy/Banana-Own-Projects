package com.example.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.listener.AsyncTestListener;
import com.example.mapper.DemoMapper;
import com.example.model.entity.Demo;
import com.example.service.demoService;
import lombok.experimental.Accessors;
import org.apache.catalina.core.AsyncContextImpl;
import org.springframework.stereotype.Service;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author banana
 * @create 2023-12-05 18:52
 */
@Service
public class demoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements demoService {

    ConcurrentHashMap<Long, AsyncContext> map = new ConcurrentHashMap();

    @Override
    public void solve(Long id, HttpServletRequest request, HttpServletResponse respons) {
        // 创建AsyncContext
        AsyncContext asyncContext = request.startAsync(request, respons);

        // 设置处理超时时间8s(即自动返回消息)
        asyncContext.setTimeout(1000L);

        AsyncTestListener asyncTestListener = new AsyncTestListener(asyncContext);

        asyncContext.addListener(asyncTestListener);
        asyncContext.start(asyncTestListener);

        map.put(id, asyncContext);


        System.out.println("结束时的线程：" + Thread.currentThread().getName());
    }

    @Override
    public void solve2(Long id) {
        AsyncContext asyncContext = map.get(id);

        asyncContext.complete();
        map.remove(id);
    }
}
