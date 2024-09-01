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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import java.io.*;
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


    /**
     * 根据配置文件场景对应的soap请求客户端
     * @return soap请求客户端
     */
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
     * 从soap请求/响应中，根据命名空间和标签名获取其中相关内容
     * @param soapContent soap内容
     * @param SoapProtocol soap协议常量 {@link SoapProtocol}
     * @param ns 命名空间
     * @param tagName 标签名
     * @return 内容
     */
    public static String getSoapElementByTagNameNS(String soapContent, SoapProtocol SoapProtocol, String ns, String tagName) {

        // 声明出参
        String rtn = null;

        try {
            soapContent = XmlUtil.format(soapContent);

            MessageFactory msgFactory = MessageFactory.newInstance(SoapProtocol.getDesc());
            SOAPMessage message = msgFactory.createMessage(new MimeHeaders(),
                    new ByteArrayInputStream(soapContent.getBytes("UTF-8")));

            SOAPBody soapBody = message.getSOAPBody();

            NodeList resultNodes = soapBody.getElementsByTagNameNS(ns, tagName);
            if (resultNodes.getLength() > 0) {
                Node resultNode = resultNodes.item(0);
                rtn = resultNode.getTextContent();
                System.out.println(rtn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rtn;


        /*方法二：DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(soapResp.getBytes("UTF-8")));
        document.getDocumentElement().normalize();
        NodeList result1 = document.getElementsByTagName("won:result");
        if(result1.getLength() > 0) {
            String textContent = result1.item(0).getTextContent();
            System.out.println(textContent);
        }*/

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
