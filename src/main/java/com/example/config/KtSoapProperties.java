package com.example.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 金唐通用soap请求（模版号=1）
 *
 * @author banana
 * @create 2024-08-31 9:48
 */
@Data
public class KtSoapProperties extends CommonSoapProperties  {

    public void delPropertiesByTplNum() {

    }




   /* @PostConstruct
    public void init() {
        // 自定义命名空间
        Map<String, String> defaultNameSpaceDeclaration = new HashMap<String, String>();
        defaultNameSpaceDeclaration.put("won", "www.WondersHSBP.com");
        nameSpaceDeclaration = defaultNameSpaceDeclaration;

        // 自定义方法&参数
        Method method = new Method();
        method.setMethodName("won:invokeRequest");
        Param param = new Param();
        param.setName("parameter");
        method = method;

    }*/
}
