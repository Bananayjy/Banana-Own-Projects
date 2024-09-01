package com.example.factory;

import cn.hutool.core.collection.CollectionUtil;
import com.example.config.CommonSoapProperties;
import com.example.enums.TplNumEnum;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author banana
 * @create 2024-09-01 15:09
 */

public class SoapPropertiesFactory {

    public static CommonSoapProperties getSoapProperties(Integer TplNum, CommonSoapProperties commonSoapProperties) {
        // 根据TplNum获取对应的枚举类
        TplNumEnum tplNumEnum = TplNumEnum.getTplNumEnumByVal(TplNum);
        switch (tplNumEnum){
            case Custom:break;
            case KT: {
                // 自定义命名空间
                Map<String, String> defaultNameSpaceDeclaration = new HashMap<String, String>();
                defaultNameSpaceDeclaration.put("won", "www.WondersHSBP.com");
                commonSoapProperties.setNameSpaceDeclaration(defaultNameSpaceDeclaration);

                // 自定义方法&参数
                CommonSoapProperties.Method method = new CommonSoapProperties.Method();
                method.setMethodName("won:invokeRequest");
                CommonSoapProperties.Param param = new CommonSoapProperties.Param();
                param.setName("parameter");
                List<CommonSoapProperties.Param> paramList = new ArrayList<>();
                paramList.add(param);
                method.setParams(paramList);
                List<CommonSoapProperties.Method> methods = new ArrayList<>();
                methods.add(method);
                commonSoapProperties.setMethods(methods);

            }
            default:break;
        }

        return commonSoapProperties;
    }

}
