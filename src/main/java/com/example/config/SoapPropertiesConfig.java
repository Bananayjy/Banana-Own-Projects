package com.example.config;

import com.example.enums.TplNumEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author banana
 * @create 2024-09-01 16:18
 */
@Configuration
@Data
/*@ConfigurationProperties("soap")
@EnableConfigurationProperties(CommonSoapProperties.class)*/
public class SoapPropertiesConfig {

    /**
     * 模版 {@link TplNumEnum}
     * 0：自定义
     * 1：金唐请求通用模板 （默认值）
     * ……
     */
    private Integer TplNum = 1;

    @Bean
    public TstP createTstP() {
        return new TstP();
    }

}
