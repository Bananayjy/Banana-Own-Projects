package com.example.custom_log.method1_i18n.utils;

import com.example.custom_log.method1_i18n.config.MyLocaleResolver;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Component
public class I18nUtil {

    public static String getMessage(String code) {
        return getMessage(code, null);
    }

    public static String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {

        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages_zn_CN");
        String content;
        try{
            content = messageSource.getMessage(code, args, locale);
        }catch (Exception e){
            log.info("获取提示消息失败： ->",e);
            content = defaultMessage;
        }
        return content;

    }

}

