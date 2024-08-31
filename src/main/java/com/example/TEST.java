package com.example;

import cn.hutool.core.util.StrUtil;
import com.example.config.CommonSoapProperties;
import com.example.core.ElementNode;
import com.example.core.SoapClient;
import com.example.enums.SoapProtocol;
import com.example.utils.SoapUtil;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
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


        SoapClient client = SoapUtil.createClientKt("123", SoapProtocol.SOAP_1_2, "123");
        client.setParam("hahah", "4567");
        client.setParam("hahah2", "4567");
        client.setParam("hahah3", "4522");
        client.setParam("hahah4", "456722");
        client.setMethod("won:miaoMethdo", "www.baidu.com");
        client.setParam("hahah5", "111");
        client.setParam("hahah6", "22");


        SOAPBodyElement methodEle = client.getMethodEle();


        // 第一个元素的变更
        Node firstChild = methodEle.getFirstChild();
        System.out.println(firstChild);

        Node firstChild1 = firstChild.getFirstChild();
        String nodeValue = firstChild1.getNodeValue();
        firstChild1.setNodeValue("456");
        System.out.println(firstChild1.getNodeValue());



        // 查找指定端点
        QName qName = new QName("www.WondersHSBP.com", "parameter");


        // 获取指定方法（请求体中的元素）

        //  自定义方法获取方法元素
        SOAPBodyElement methodEleByName = client.getMethodEleByName("won:miaoMethdo");
        System.out.println(methodEleByName);

        // 加参数
        /*QName qName1 = new QName("test", "test", "test");
        methodEleByName.addChildElement(qName1);*/
        client.setParam(methodEleByName, "test", "123", "won");

        // 获取方法中的所有参数 √
        Node firstNode = methodEleByName.getFirstChild();
        while(firstNode != null){
            System.out.println(firstNode.getTextContent());
            firstNode = firstNode.getNextSibling();
        }


        // 找具体方法中的子节点
        Iterator invokeRequest = methodEle.getChildElements(qName);
        while(invokeRequest.hasNext()) {
            Node next = (Node)invokeRequest.next();
            String textContent = next.getTextContent();

            Node nextSibling = next.getNextSibling();
            while(nextSibling != null) {
                System.out.println(nextSibling.getTextContent());
                nextSibling = nextSibling.getNextSibling();
            }

        }


        System.out.println(client.getMsgStr(true));

    }
}
