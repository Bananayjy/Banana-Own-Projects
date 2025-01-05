package com.example.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;

/**
 * @author banana
 * @create 2025-01-04 17:00
 */
// 插件签名，需要拦截哪个对象
@Intercepts(
        {
                // type：需要拦截对象的class对象 method：需要拦截的方法  入参：方法可能有重载的情况，入参情况
                @Signature(type= StatementHandler.class, method = "parameterize", args = Statement.class )
        }
)
public class MyPlugin2 implements Interceptor {

    /**
     * 拦截目标对象的目标方法执行
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("拦截目标对象执行（2）：" + invocation.getMethod());
        // 执行目标方法
        Object proceed = invocation.proceed();
        return proceed;
    }

    /**
     * 包装目标对象（为目标对象创建一个代理对象）
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        System.out.println("将要包装的对象（2）：" + target);
        // 借助Plugin的wrap方法来使用当前Interceptor包装我们的目标对象
        // 也可以使用 Interceptor.super.plugin(target)
        Object wrap = Plugin.wrap(target, this);
        // 返回当前target创建的动态代理
        return wrap;
    }

    /**
     * 将插件注册时的property属性设置进来
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置信息（2）：" + properties);
        Interceptor.super.setProperties(properties);
    }
}
