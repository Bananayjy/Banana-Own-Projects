package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 金唐通用soap请求
 *
 * 绑定示例：
 * kt-soap:
 *   method:
 *     methodName: "exampleMethod"
 *     namespace: "http://example.com/namespace"
 *     params:
 *       - name: "param1"
 *         value: "value1"
 *       - name: "param2"
 *         value: 123
 *       - name: "param3"
 *         value: true
 *
 * @author banana
 * @create 2024-08-31 9:48
 */
@Data
@Configuration
@ConfigurationProperties("kt-soap")
public class KtSoapProperties {

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
