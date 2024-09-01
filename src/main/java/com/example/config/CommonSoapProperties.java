package com.example.config;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.example.enums.TplNumEnum;
import com.example.factory.SoapPropertiesFactory;
import com.example.utils.SpringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO 配置文件创建
 * 通用soap请求配置
 * @author banana
 * @create 2024-08-29 17:54
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties("soap")
public class CommonSoapProperties {

    /**
     * 请求url地址
     */
    private String url;

    /**
     * soap命名空间前缀
     */
    private String nameSpacePrefix;

    /**
     * 是否使用CDATA
     */
    private boolean ifCDATA;

    /**
     * 模版 {@link TplNumEnum}
     * 0：自定义
     * 1：金唐请求通用模板 （默认值）
     * ……
     */
    private Integer TplNum = 1;

    /**
     * 自定义命名空间
     * key:名称 value:命名空间
     */
    private Map<String, String> nameSpaceDeclaration;

    /**
     * 方法
     */
    /*@Nullable*/
    private List<Method> methods;


    /*public abstract void delPropertiesByTplNum();*/

    /*@Bean
    public TstP createTstP() {
        return new TstP();
    }*/

    @Data
    public static class Method {

        /**
         * 方法名称
         */
        /*@NonNull*/
        private String methodName;

        /**
         * 方法命名空间，可不填
         */
        /*@Nullable*/
        private String namespace;

        /*@Nullable*/
        private List<Param> params;
    }


    @Data
    public static class Param {

        /*@NonNull*/
        private String name;

        /*@NonNull*/
        private Object value;
    }

    @PostConstruct
    public void init() {
        log.info("===== 通用soap请求配置开始初始化 =====");
        log.info("当前信息:{}", this);

        // 根据配置中的TplNum进行配置修改
        CommonSoapProperties commonSoapProperties = SoapPropertiesFactory.getSoapProperties(TplNum, this);
        System.out.println(commonSoapProperties);
    }

}
