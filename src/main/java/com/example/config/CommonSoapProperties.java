package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author banana
 * @create 2024-08-29 17:54
 */
@Data
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
     * 自定义命名空间
     * key:名称 value:命名空间
     */
    private Map<String, String> nameSpaceDeclaration;



}
