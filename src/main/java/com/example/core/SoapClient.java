package com.example.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpBase;
import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.webservice.SoapRuntimeException;

import com.example.enums.SoapProtocol;
import com.example.utils.SoapUtil;
import com.sun.xml.internal.messaging.saaj.soap.impl.ElementImpl;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @author banana
 * @create 2024-08-30 0:28
 */
public class SoapClient extends HttpBase<SoapClient> {

    private static final String CONTENT_TYPE_SOAP11_TEXT_XML = "text/xml;charset=";

    private static final String CONTENT_TYPE_SOAP12_SOAP_XML = "application/soap+xml;charset=";

    private String url;

    private int connectionTimeout;

    private int readTimeout;

    private MessageFactory factory;

    private SOAPMessage message;

    private SOAPBodyElement methodEle;

    private final String namespaceURI;

    private final SoapProtocol protocol;

    public static SoapClient create(String url) {
        return new SoapClient(url);
    }

    public static SoapClient create(String url, SoapProtocol protocol) {
        return new SoapClient(url, protocol);
    }

    public static SoapClient create(String url, SoapProtocol protocol, String namespaceURI) {
        return new SoapClient(url, protocol, namespaceURI);
    }

    public SoapClient(String url) {
        // 默认使用SOAP1.2版本
        this(url, SoapProtocol.SOAP_1_2);
    }

    public SoapClient(String url, SoapProtocol protocol) {
        this(url, protocol, (String)null);
    }

    public SoapClient(String url, SoapProtocol protocol, String namespaceURI) {
        this.connectionTimeout = HttpGlobalConfig.getTimeout();
        this.readTimeout = HttpGlobalConfig.getTimeout();
        this.url = url;
        this.namespaceURI = namespaceURI;
        this.protocol = protocol;
        this.init(protocol);
    }

    public SoapClient init(SoapProtocol protocol) {
        try {
            this.factory = MessageFactory.newInstance(protocol.getDesc());
            this.message = this.factory.createMessage();
            return this;
        } catch (SOAPException var3) {
            SOAPException e = var3;
            throw new SoapRuntimeException(e);
        }
    }

    public SoapClient reset() {
        try {
            this.message = this.factory.createMessage();
        } catch (SOAPException var2) {
            SOAPException e = var2;
            throw new SoapRuntimeException(e);
        }

        this.methodEle = null;
        return this;
    }


    public SoapClient setUrl(String url) {
        this.url = url;
        return this;
    }

    public SoapClient setPrefix(String prefix) {
        try {
            SOAPEnvelope envelope = this.message.getSOAPPart().getEnvelope();
            String orginPrefix = envelope.getPrefix();
            envelope.setPrefix(prefix);
            envelope.getBody().setPrefix(prefix);
            envelope.getHeader().setPrefix(prefix);
            envelope.removeNamespaceDeclaration(orginPrefix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public SoapClient addNameSpaceDeclaration(Map<String, String> nameSpaceDeclaration) {
        try {
            SOAPEnvelope envelope = this.message.getSOAPPart().getEnvelope();
            nameSpaceDeclaration.forEach((k, v) -> {
                try {
                    envelope.addNamespaceDeclaration(k, v);
                } catch (SOAPException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public SOAPHeaderElement addSOAPHeader(QName name, String actorURI, String roleUri, Boolean mustUnderstand, Boolean relay) {
        SOAPHeaderElement ele = this.addSOAPHeader(name);

        try {
            if (StrUtil.isNotBlank(roleUri)) {
                ele.setRole(roleUri);
            }

            if (null != relay) {
                ele.setRelay(relay);
            }
        } catch (SOAPException var8) {
            SOAPException e = var8;
            throw new SoapRuntimeException(e);
        }

        if (StrUtil.isNotBlank(actorURI)) {
            ele.setActor(actorURI);
        }

        if (null != mustUnderstand) {
            ele.setMustUnderstand(mustUnderstand);
        }

        return ele;
    }

    public SOAPHeaderElement addSOAPHeader(String localName) {
        return this.addSOAPHeader(new QName(localName));
    }

    public SOAPHeaderElement addSOAPHeader(String localName, String value) {
        SOAPHeaderElement soapHeaderElement = this.addSOAPHeader(localName);
        soapHeaderElement.setTextContent(value);
        return soapHeaderElement;
    }

    public SOAPHeaderElement addSOAPHeader(QName name) {
        try {
            SOAPHeaderElement ele = this.message.getSOAPHeader().addHeaderElement(name);
            return ele;
        } catch (SOAPException var4) {
            SOAPException e = var4;
            throw new SoapRuntimeException(e);
        }
    }

    public SoapClient setMethod(Name name, Map<String, Object> params, boolean useMethodPrefix) {
        return this.setMethod(new QName(name.getURI(), name.getLocalName(), name.getPrefix()), params, useMethodPrefix);
    }

    public SoapClient setMethod(QName name, Map<String, Object> params, boolean useMethodPrefix) {
        this.setMethod(name);
        String prefix = useMethodPrefix ? name.getPrefix() : null;
        SOAPBodyElement methodEle = this.methodEle;
        Iterator var6 = MapUtil.wrap(params).iterator();

        while(var6.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry)var6.next();
            setParam(methodEle, (String)entry.getKey(), entry.getValue(), prefix);
        }

        return this;
    }

    public SoapClient setMethod(String methodName) {
        return this.setMethod(methodName, (String) ObjectUtil.defaultIfNull(this.namespaceURI, ""));
    }

    public SoapClient setMethod(String methodName, String namespaceURI) {
        List<String> methodNameList = StrUtil.split(methodName, ':');
        QName qName;
        if (2 == methodNameList.size()) {
            qName = new QName(namespaceURI, (String)methodNameList.get(1), (String)methodNameList.get(0));
        } else {
            qName = new QName(namespaceURI, methodName);
        }

        return this.setMethod(qName);
    }

    public SoapClient setMethod(QName name) {
        try {
            this.methodEle = this.message.getSOAPBody().addBodyElement(name);
            return this;
        } catch (SOAPException var3) {
            SOAPException e = var3;
            throw new SoapRuntimeException(e);
        }
    }

    public SoapClient setParam(String name, Object value) {
        return this.setParam(name, value, true);
    }

    public SoapClient setParam(String name, Object value, boolean useMethodPrefix) {
        setParam(this.methodEle, name, value, useMethodPrefix ? this.methodEle.getPrefix() : null);
        return this;
    }

    public SoapClient setParams(Map<String, Object> params) {
        return this.setParams(params, true);
    }

    public SoapClient setParams(Map<String, Object> params, boolean useMethodPrefix) {
        Iterator var3 = MapUtil.wrap(params).iterator();

        while(var3.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry)var3.next();
            this.setParam((String)entry.getKey(), entry.getValue(), useMethodPrefix);
        }

        return this;
    }

    public SOAPBodyElement getMethodEle() {
        return this.methodEle;
    }


    /**
     * 根据方法名称获取对应的方法元素
     * @param method
     * @return
     */
    public SOAPBodyElement getMethodEleByName(String method) {
        return getMethodEleByNameAndNUrl(method, "");
    }

    /**
     * 根据方法名称&命名空间获取对应的方法元素
     * @param method
     * @return
     */
    public SOAPBodyElement getMethodEleByNameAndNUrl(String method, String namespaceURI) {
        String[] split = method.split(":");
        QName qName = null;
        if(split.length == 2) {
            String prefix = split[0];
            String methodName = split[1];
            qName = new QName(namespaceURI, methodName, prefix);
        } else {
            String methodName = method;
            qName = new QName(namespaceURI, methodName);
        }
        return getMethodEleByQName(qName);
    }


    /**
     * 获取指定的方法元素
     * @param qName qname入参
     * @return
     */
    private SOAPBodyElement getMethodEleByQName(QName qName) {

        // 声明出参
        SOAPBodyElement rtn = null;

        // 获取body中的元素（即方法）
        SOAPBodyElement soapBodyElement = getMethodEle();

        // qName相关参数
        String prefix = qName.getPrefix();
        String localPart = qName.getLocalPart();
        String namespaceURI = qName.getNamespaceURI();

        // 从后向前遍历
        while(soapBodyElement != null) {
            // 当前元素的QName
            QName elementQName = soapBodyElement.getElementQName();

            if(Objects.equals(elementQName.getPrefix(), prefix)
                    && Objects.equals(elementQName.getLocalPart(), localPart)) {
                rtn = soapBodyElement;
                break;
            }
            soapBodyElement = (SOAPBodyElement) soapBodyElement.getPreviousSibling();
        }
        return rtn;
    }


    public SOAPElement getParamFromMethodByName(SOAPBodyElement method, String paramName) {
        return null;
    }

    private SOAPElement getParamFromMethodByQName(SOAPBodyElement method, QName paramQName) {

        // 获取头部的参数
        SOAPElement headParam = (SOAPElement) method.getFirstChild();

        while(headParam != null) {

            headParam = (SOAPElement) headParam.getNextSibling();
        }

        return null;
    }

    public SOAPMessage getMessage() {
        return this.message;
    }



    public SoapClient write(OutputStream out) {
        try {
            this.message.writeTo(out);
            return this;
        } catch (IOException | SOAPException var3) {
            Exception e = var3;
            throw new SoapRuntimeException(e);
        }
    }

    public SoapClient timeout(int milliseconds) {
        this.setConnectionTimeout(milliseconds);
        this.setReadTimeout(milliseconds);
        return this;
    }

    public SoapClient setConnectionTimeout(int milliseconds) {
        this.connectionTimeout = milliseconds;
        return this;
    }

    public SoapClient setReadTimeout(int milliseconds) {
        this.readTimeout = milliseconds;
        return this;
    }

    public SOAPMessage sendForMessage() {
        HttpResponse res = this.sendForResponse();
        MimeHeaders headers = new MimeHeaders();
        Iterator var3 = res.headers().entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, List<String>> entry = (Map.Entry)var3.next();
            if (StrUtil.isNotEmpty((CharSequence)entry.getKey())) {
                headers.setHeader((String)entry.getKey(), (String) CollUtil.get((Collection)entry.getValue(), 0));
            }
        }

        SOAPMessage var11;
        try {
            var11 = this.factory.createMessage(headers, res.bodyStream());
        } catch (SOAPException | IOException var8) {
            Exception e = var8;
            throw new SoapRuntimeException(e);
        } finally {
            IoUtil.close(res);
        }

        return var11;
    }

    public String send() {
        return this.send(false);
    }

    public String send(boolean pretty) {
        String body = this.sendForResponse().body();
        return pretty ? XmlUtil.format(body) : body;
    }

    public HttpResponse sendForResponse() {
        return ((HttpRequest)HttpRequest.post(this.url).setFollowRedirects(true).setConnectionTimeout(this.connectionTimeout).setReadTimeout(this.readTimeout).contentType(this.getXmlContentType()).header(this.headers())).body(this.getMsgStr(false)).executeAsync();
    }

    private String getXmlContentType() {
        switch (this.protocol) {
            case SOAP_1_1:
                return "text/xml;charset=".concat(this.charset.toString());
            case SOAP_1_2:
                return "application/soap+xml;charset=".concat(this.charset.toString());
            default:
                throw new SoapRuntimeException("Unsupported protocol: {}", new Object[]{this.protocol});
        }
    }

    public String getMsgStr(boolean pretty) {
        return SoapUtil.toString(this.message, pretty, this.charset);
    }

    public static SOAPElement setParam(SOAPElement ele, String name, Object value, String prefix) {
        SOAPElement childEle;
        SOAPException e;
        try {
            if (StrUtil.isNotBlank(prefix)) {
                childEle = ele.addChildElement(name, prefix);
            } else {
                childEle = ele.addChildElement(name);
            }
        } catch (SOAPException var9) {
            e = var9;
            throw new SoapRuntimeException(e);
        }

        if (null != value) {
            if (value instanceof SOAPElement) {
                try {
                    ele.addChildElement((SOAPElement)value);
                } catch (SOAPException var8) {
                    e = var8;
                    throw new SoapRuntimeException(e);
                }
            } else if (value instanceof Map) {
                Iterator var6 = ((Map)value).entrySet().iterator();

                while(var6.hasNext()) {
                    Object obj = var6.next();
                    Map.Entry entry = (Map.Entry)obj;
                    setParam(childEle, entry.getKey().toString(), entry.getValue(), prefix);
                }
            } else {
                childEle.setValue(value.toString());
            }
        }

        return childEle;
    }
}
