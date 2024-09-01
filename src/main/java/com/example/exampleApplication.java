package com.example;

import com.example.config.CommonSoapProperties;
import com.example.utils.SoapUtil;
import com.example.utils.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author banana
 * @create 2024-09-01 10:39
 */
@SpringBootApplication
public class exampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(exampleApplication.class, args);
        Object commonSoapProperties = SpringUtils.getBean("commonSoapProperties");
        System.out.println(commonSoapProperties);

        SoapUtil.careatClientByProperties();

    }
}
