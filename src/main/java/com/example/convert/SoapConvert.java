package com.example.convert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 *
 * @author banana
 * @create 2024-09-01 21:20
 */
public class SoapConvert {

    private static String CDATAStr = "<![CDATA[*]]>";

    /**
     * 对象转Xml格式
     * @param object 对象，字段需要使用相关注解修饰{@link javax.xml.bind.annotation.XmlElement}
     * @param ifCDATA 是否CDATA格式
     * @return xml格式
     */
    public static <T> String ObjectToXml(T object, Class<T> clazz, boolean ifCDATA) {

        // 声明出参
        String xmlString = null;

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
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

    /**
     * xml格式转实体类
     * @param xml xml格式字符串
     * @param clazz 需要转换的实体类类对象
     * @return 实体类对象结果值
     * @param <T> 实体类对象类型
     */
    public static <T> T XmlToObject(String xml, Class<T> clazz) {

        // 声明出参对象
        T rtn = null;

        // 解析xml
        try {
            // 创建 JAXBContext 对象
            JAXBContext context = JAXBContext.newInstance(clazz);

            // 创建 Unmarshaller 对象
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // 将 xml 内容转换为 T 对象
            StringReader reader = new StringReader(xml);
            rtn = (T) unmarshaller.unmarshal(reader);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }
}
