package com.example.utils;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.XmlUtil;

import cn.hutool.http.webservice.SoapRuntimeException;
import com.example.core.SoapClient;
import com.example.enums.SoapProtocol;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

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
