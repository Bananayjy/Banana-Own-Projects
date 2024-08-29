package com.example.utils;

import cn.hutool.core.util.StrUtil;
import com.example.config.CommonSoapProperties;
import com.example.core.ElementNode;
import com.example.core.SoapClient;
import com.example.enums.SoapProtocol;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.xml.soap.*;
import java.io.IOException;
import java.util.*;

/**
 * @author banana
 * @create 2024-08-29 20:48
 */
@Slf4j
public class TEST {
    // 通用配置文件
    private CommonSoapProperties commonSoapProperties;

    // 默认命名空间
    static Map<String, String> defaultNameSpaceDeclaration = new HashMap<>();

    // 默认请求头
    static List<ElementNode> defualtHeadElementNode = new ArrayList<>();

    // 默认请求体
    static List<ElementNode> defaultBodyElementNode = new ArrayList<>();

    // 命名空间的前缀
    final private String nameSpacePrefix = "soapenv";



    public String getSoapBody(Map<String, String> nameSpaceDeclaration, List<ElementNode> headElementNode, List<ElementNode> bodyElementNode){
        String ret = null;
        try{



        } catch (Exception ex) {
            log.error("生成soap请求消息时发生异常：{}", ex.getMessage(), ex);
        }

        return ret;
    }

    // 构建soap请求体
    static private void SoapBodyBuilder(SOAPBody body, List<ElementNode> bodyElementNode) throws SOAPException {
        for (ElementNode elementNode : bodyElementNode) {
            String value = elementNode.getValue();
            String nameSpace = elementNode.getNamespace();
            SOAPElement root = body.addChildElement(value, nameSpace);
            SoapBodyBuilderRecursion(root, elementNode);
        }
        return;
    }

    static private void SoapBodyBuilderRecursion(SOAPElement root, ElementNode elementNode) throws SOAPException {
        SOAPElement soapElement = root.addChildElement(elementNode.getValue(), elementNode.getNamespace());
        if(StrUtil.isNotBlank(elementNode.getContent())) {
            soapElement.addTextNode(elementNode.getContent());
        }
        for (ElementNode child : elementNode.getChildren()) {
            SoapBodyBuilderRecursion(soapElement, child);
        }
        return;
    }

    // 初始化默认值
    @PostConstruct
    public void initDefaultValue(){
        // 默认请求头（空）
        defaultNameSpaceDeclaration.put("won", "www.WondersHSBP.com");
        defaultNameSpaceDeclaration.put("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");

        // 默认请求体
        ElementNode rootElementNode = new ElementNode("invokeRequest", "won");
        ElementNode childElementNode = new ElementNode("parameter", "won");
        rootElementNode.addChildElementNode(childElementNode);
        defaultBodyElementNode.add(rootElementNode);

        /*getSoapMessage(null);*/


    }


    public static void main(String[] args) throws SOAPException, IOException {


        // 直接使用hutool的Soap构造Soap的请求体
        /*SoapClient client = SoapUtil.createClient("http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx",
                        SoapProtocol.SOAP_1_2, "www.WondersHSBP.com")
                .setMethod("invokeRequest", "123")
                .setParam("parameter", "123")
                .setParam("parameter2", "321")
                .setMethod("won1:invokeRequest2")
                .setParam("param", 1);*/


        SoapClient client = SoapUtil.createClient("123", SoapProtocol.SOAP_1_2);
        // 自定义soap命名空间前缀
        client.setPrefix("abc");
        Map<String, String> nameSpaceDeclaration = new HashMap<>();

        // 自定义命名空间
        nameSpaceDeclaration.put("won", "www.WondersHSBP.com");
        client.addNameSpaceDeclaration(nameSpaceDeclaration);

        client.setMethod("won:method");

        System.out.println(client.getMsgStr(true));

    }
}
