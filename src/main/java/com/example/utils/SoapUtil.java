package com.example.utils;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;

import cn.hutool.http.webservice.SoapRuntimeException;
import com.example.config.CommonSoapProperties;
import com.example.core.SoapClient;
import com.example.entity.Body;
import com.example.enums.SoapProtocol;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author banana
 * @create 2024-08-30 0:26
 */
public class SoapUtil {

    private static String CDATAStr = "<![CDATA[*]]>";

    public static SoapClient createClient(String url) {
        return SoapClient.create(url);
    }

    public static SoapClient createClient(String url, SoapProtocol protocol) {
        return SoapClient.create(url, protocol);
    }

    public static SoapClient createClient(String url, SoapProtocol protocol, String namespaceURI) {
        return SoapClient.create(url, protocol, namespaceURI);
    }

    /**
     * 快速创建金唐通用的soap请求接口
     * @param url 请求地址
     * @param soapProtocol soap协议版本
     * @param param 请求参数
     * @return soap请求客户端
     */
    public static SoapClient createClientKt(String url, SoapProtocol soapProtocol, String param) {
        Map<String, String> defaultNameSpaceDeclaration = new HashMap<String, String>();
        defaultNameSpaceDeclaration.put("won", "www.WondersHSBP.com");
        return createClient(url, soapProtocol).addNameSpaceDeclaration(defaultNameSpaceDeclaration)
                .setMethod("won:invokeRequest").setParam("parameter", param);
    }


    public static SoapClient careatClientByProperties() {
        CommonSoapProperties commonSoapProperties = SpringUtils.getBean("commonSoapProperties");
        System.out.println(commonSoapProperties);
        // todo 值的填充一并完成，在配置文件中配置？
        String url = commonSoapProperties.getUrl();
        String nameSpacePrefix = commonSoapProperties.getNameSpacePrefix();
        Map<String, String> nameSpaceDeclaration = commonSoapProperties.getNameSpaceDeclaration();
        List<CommonSoapProperties.Method> methods = commonSoapProperties.getMethods();

        SoapClient soapClient = createClient(url).setPrefix(nameSpacePrefix)
                .addNameSpaceDeclaration(nameSpaceDeclaration);

        for (CommonSoapProperties.Method method : methods) {
            String methodName = method.getMethodName();
            String namespace = method.getNamespace();
            if(StrUtil.isBlank(namespace)) {
                soapClient.setMethod(methodName);
            } else {
                soapClient.setMethod(methodName, namespace);
            }
            List<CommonSoapProperties.Param> params = method.getParams();
            Map<String, Object> collect = params.stream().collect(Collectors.toMap(r -> r.getName(), r -> r.getValue(), (r1, r2) -> r1));
            soapClient.setParams(collect);
        }
        return soapClient;
    }

    /**
     * 对象转Xml格式
     * @param object 对象，字段需要使用相关注解修饰{@link javax.xml.bind.annotation.XmlElement}
     * @param ifCDATA 是否CDATA格式
     * @return xml格式
     */
    public static String ObjectToXml(Object object, boolean ifCDATA) {

        // 声明出参
        String xmlString = null;

        try {
            JAXBContext context = JAXBContext.newInstance(Body.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            marshaller.marshal(object, System.out);

            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);

            xmlString = writer.toString();
            System.out.println(xmlString);

            // 去掉格式定义
            xmlString = xmlString.replaceFirst("<.*?>", "");

            if(ifCDATA) {
                xmlString = CDATAStr.replace("*", xmlString);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return xmlString;
    }

    public static String toString(SOAPMessage message, boolean pretty) {
        return toString(message, pretty, CharsetUtil.CHARSET_UTF_8);
    }

    public static String toString(SOAPMessage message, boolean pretty, Charset charset) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            message.writeTo(out);
        } catch (IOException | SOAPException var7) {
            Exception e = var7;
            throw new SoapRuntimeException(e);
        }

        String messageToString;
        try {
            messageToString = out.toString(charset.toString());
        } catch (UnsupportedEncodingException var6) {
            UnsupportedEncodingException e = var6;
            throw new UtilException(e);
        }

        return pretty ? XmlUtil.format(messageToString) : messageToString;
    }

}
