package com.example.custom_log.method1_i18n.test;

import com.example.custom_log.method1_i18n.utils.I18nUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 非web环境下的i18n使用
 * @author banana
 * @create 2024-09-24 23:26
 */
public class I18nExample {

    public static void main(String[] args) {
        // 设置区域，例如中文
        /*Locale locale = new Locale("zh", "CN");

        ResourceBundle messages = ResourceBundle.getBundle("i18n/messages_zn_CN", locale);

        String str = messages.getString("A1");

        System.out.println(str);*/

        // 工具类实现爱你
        Object[] params = new Object[]{"你是", "啊456"};
        String a1 = I18nUtil.getMessage("A2", params);
        System.out.println(a1);

    }

}

