package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
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
     * 是否使用CDATA
     */
    private boolean ifCDATA;

    /**
     * 自定义命名空间
     * key:名称 value:命名空间
     */
    private Map<String, String> nameSpaceDeclaration;


    /**
     * 自定义方法
     */

    /**
     * 方法
     */
    @Nullable
    private Method method;


    @Data
    static class Method {

        /**
         * 方法名称
         */
        @NonNull
        private String methodName;

        /**
         * 方法命名空间，可不填
         */
        @Nullable
        private String namespace;

        @Nullable
        private List<Param> params = new ArrayList<>();
    }


    @Data
    static class Param {

        @NonNull
        private String name;

        @NonNull
        private Object value;
    }

}
