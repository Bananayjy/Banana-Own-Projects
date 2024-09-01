package com.example;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.example.config.CommonSoapProperties;
import com.example.core.ElementNode;
import com.example.core.SoapClient;
import com.example.entity.Body;
import com.example.entity.PubEmpKszr;
import com.example.entity.Response;
import com.example.enums.SoapProtocol;
import com.example.utils.SoapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * @author banana
 * @create 2024-08-29 20:48
 */
@Component
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

    static String CDATAStr = "<![CDATA[*]]>";



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

    // 请求相关测试
    /*public static void main(String[] args) throws SOAPException, IOException {

        // 直接使用hutool的Soap构造Soap的请求体
        *//*SoapClient client = SoapUtil.createClient("http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx",
                        SoapProtocol.SOAP_1_2, "www.WondersHSBP.com")
                .setMethod("invokeRequest", "123")
                .setParam("parameter", "123")
                .setParam("parameter2", "321")
                .setMethod("won1:invokeRequest2")
                .setParam("param", 1);*//*


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

        // 给指定方法添加参数
        *//*QName qName1 = new QName("test", "test", "test");
        methodEleByName.addChildElement(qName1);*//*
        client.setParam(methodEleByName, "test", "123");

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


        System.out.println(client.getMsgStr(false));

        System.out.println(client.getMsgStr(true));

        //client.send();

    }*/


    // 入参测试
    public static void main(String[] args) {

        // 根据场景的实体类生成

        // Create sample data
        PubEmpKszr kszr1 = new PubEmpKszr();
        kszr1.setId("110");
        kszr1.setName("皮肤科");
        kszr1.setKzrId("13065");
        kszr1.setKzr("章森苗");

        PubEmpKszr kszr2 = new PubEmpKszr();
        kszr2.setId("77");
        kszr2.setName("物业公司");

        Response response = new Response();
        response.setRetCode("0");
        response.setRetInfo("success");
        response.setPubEmpKszrList(new ArrayList<>());
        response.getPubEmpKszrList().add(kszr1);
        response.getPubEmpKszrList().add(kszr2);

        Body body = new Body();
        body.setResponse(response);

        String s = SoapUtil.ObjectToXml(body, false);
        System.out.println(s);


    }



    // 响应相关测试
    /*public static void main(String[] args) {
        String respBody = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "   <soap:Body>\n" +
                "      <won:invokeResponse xmlns:won=\"www.WondersHSBP.com\">\n" +
                "         <!--type: string-->\n" +
                "         <won:result><![CDATA[<body>\n" +
                "   <response>\n" +
                "<ret_code>0</ret_code>\n" +
                "<ret_info>success</ret_info>\n" +
                "<PUB_EMP_KSZR><ID>110</ID><NAME>皮肤科</NAME><KZR_ID>13065</KZR_ID><KZR>章森苗</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>77</ID><NAME>物业公司</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>96</ID><NAME>十五楼报告厅</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>101</ID><NAME>警务室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>124</ID><NAME>肛肠科</NAME><KZR_ID>11097</KZR_ID><KZR>邵小彬</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>102</ID><NAME>内一病区医办</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>115</ID><NAME>康复科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>128</ID><NAME>儿科病区医办</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>129</ID><NAME>肛肠病区医办</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>130</ID><NAME>内二病区医办</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>90</ID><NAME>顾问室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>88</ID><NAME>人事科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>89</ID><NAME>工会团支部</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>91</ID><NAME>文印室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>92</ID><NAME>接待室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>94</ID><NAME>科教科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>95</ID><NAME>病案统计室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>97</ID><NAME>病区药房</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>105</ID><NAME>电梯机房</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>32</ID><NAME>中药房</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>118</ID><NAME>内一(呼吸科)</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>119</ID><NAME>内一(消化科)</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>120</ID><NAME>内二(内分泌)</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>121</ID><NAME>ICU</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>133</ID><NAME>手术室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>126</ID><NAME>方便门诊</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>134</ID><NAME>内科门急诊</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>147</ID><NAME>老干部肿瘤</NAME><KZR_ID>11108</KZR_ID><KZR>余锟</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>148</ID><NAME>老干部呼吸</NAME><KZR_ID>11115</KZR_ID><KZR>欧阳雪飞</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>150</ID><NAME>病区药房骨伤</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>152</ID><NAME>公共卫生科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>153</ID><NAME>采购中心</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>154</ID><NAME>儿科2</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>155</ID><NAME>普外泌尿病区</NAME><KZR_ID>13110</KZR_ID><KZR>钟雪</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>156</ID><NAME>耳鼻咽喉病区</NAME><KZR_ID>13089</KZR_ID><KZR>周小萍</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>158</ID><NAME>门急诊内科</NAME><KZR_ID>13065</KZR_ID><KZR>章森苗</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>159</ID><NAME>门急诊外科</NAME><KZR_ID>13065</KZR_ID><KZR>章森苗</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>157</ID><NAME>九龙湖隔离点</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>160</ID><NAME>全科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>161</ID><NAME>测试</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>162</ID><NAME>心内科病区</NAME><KZR_ID>13165</KZR_ID><KZR>吴寅芬</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>163</ID><NAME>心血管神经科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>168</ID><NAME>病区药房儿二</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>164</ID><NAME>精神卫生科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>166</ID><NAME>病区药房普外</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>167</ID><NAME>病区药房妇科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>169</ID><NAME>病区药房神经</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>171</ID><NAME>急诊护理</NAME><KZR_ID>13194</KZR_ID><KZR>牛丽娜</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>170</ID><NAME>治未病科</NAME><KZR_ID>11043</KZR_ID><KZR>包科颖</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>174</ID><NAME>门诊护理</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>172</ID><NAME>心血管内科</NAME><KZR_ID>13175</KZR_ID><KZR>蔡燕军</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>173</ID><NAME>神经内科</NAME><KZR_ID>13177</KZR_ID><KZR>刘艳</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>176</ID><NAME>泌尿外科</NAME><KZR_ID>13056</KZR_ID><KZR>刘芸</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>70</ID><NAME>图书室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>71</ID><NAME>医院</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>66</ID><NAME>营养科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>69</ID><NAME>专家门诊</NAME><KZR_ID>11155</KZR_ID><KZR>张玲玲</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>61</ID><NAME>肠道科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>63</ID><NAME>木工房</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>64</ID><NAME>宿舍</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>65</ID><NAME>洗衣房</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>72</ID><NAME>九龙湖卫生院</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>80</ID><NAME>重症医学病区</NAME><KZR_ID>13098</KZR_ID><KZR>陈燕枫</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>82</ID><NAME>儿科病区</NAME><KZR_ID>11175</KZR_ID><KZR>薛瑾瑜</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>83</ID><NAME>肛肠妇科病区</NAME><KZR_ID>11183</KZR_ID><KZR>罗明英</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>85</ID><NAME>呼吸感染病区</NAME><KZR_ID>13100</KZR_ID><KZR>刘丹</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>86</ID><NAME>肿瘤科病区</NAME><KZR_ID>11203</KZR_ID><KZR>裘琼瑶</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>87</ID><NAME>食堂</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>113</ID><NAME>消控中心</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>117</ID><NAME>膏方</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>122</ID><NAME>发热门诊</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>127</ID><NAME>门诊办公室</NAME><KZR_ID>711155</KZR_ID><KZR>张玲玲</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>103</ID><NAME>设备科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>78</ID><NAME>骨伤康复病区</NAME><KZR_ID>11167</KZR_ID><KZR>司徒海燕</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>79</ID><NAME>消化内科病区</NAME><KZR_ID>11193</KZR_ID><KZR>范月婷</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>108</ID><NAME>中医经典肿瘤</NAME><KZR_ID>11108</KZR_ID><KZR>余锟</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>106</ID><NAME>理疗科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>136</ID><NAME>病区药房肛肠</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>135</ID><NAME>病区药房儿科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>137</ID><NAME>病区药房内一</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>138</ID><NAME>病区药房内二</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>139</ID><NAME>病区药房内三</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>140</ID><NAME>病区药房十四</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>141</ID><NAME>病区药房输液</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>142</ID><NAME>病区药房手术</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>143</ID><NAME>质控办</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>144</ID><NAME>骨伤科</NAME><KZR_ID>113005</KZR_ID><KZR>张坚若</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>145</ID><NAME>重症监护科</NAME><KZR_ID>13076</KZR_ID><KZR>李鹏飞</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>146</ID><NAME>老干部消化</NAME><KZR_ID>11101</KZR_ID><KZR>邱奕</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>54</ID><NAME>糖尿病专科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>116</ID><NAME>中医保胎门诊</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>93</ID><NAME>小会议室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>151</ID><NAME>总会计师室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>111</ID><NAME>总务仓库</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>99</ID><NAME>病理科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>100</ID><NAME>腔镜中心</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>107</ID><NAME>十二楼医办</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>104</ID><NAME>院感科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>112</ID><NAME>病区加床备用</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>123</ID><NAME>设备科仓库</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>131</ID><NAME>呼吸感染科</NAME><KZR_ID>13187</KZR_ID><KZR>张渊</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>132</ID><NAME>招宝山社区</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>73</ID><NAME>自然疗法门诊</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>10</ID><NAME>中医科</NAME><KZR_ID>11040</KZR_ID><KZR>樊志明</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>11</ID><NAME>消化内科</NAME><KZR_ID>13152</KZR_ID><KZR>倪卫国</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>12</ID><NAME>普外科</NAME><KZR_ID>13056</KZR_ID><KZR>刘芸</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>13</ID><NAME>骨伤科(门诊)</NAME><KZR_ID>113005</KZR_ID><KZR>张坚若</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>6</ID><NAME>辅助科室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>14</ID><NAME>针灸科</NAME><KZR_ID>11070</KZR_ID><KZR>杜小娜</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>15</ID><NAME>推拿科</NAME><KZR_ID>11070</KZR_ID><KZR>杜小娜</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>16</ID><NAME>口腔科</NAME><KZR_ID>13203</KZR_ID><KZR>潘建</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>17</ID><NAME>中医妇科</NAME><KZR_ID>11040</KZR_ID><KZR>樊志明</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>18</ID><NAME>妇科</NAME><KZR_ID>13051</KZR_ID><KZR>孙波娜</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>19</ID><NAME>耳鼻咽喉科</NAME><KZR_ID>13073</KZR_ID><KZR>张欣荣</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>20</ID><NAME>眼科</NAME><KZR_ID>13244</KZR_ID><KZR>余海清</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>21</ID><NAME>儿科</NAME><KZR_ID>11084</KZR_ID><KZR>毕美芬</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>22</ID><NAME>检验科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>23</ID><NAME>特检科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>24</ID><NAME>放射科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>25</ID><NAME>麻醉科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>28</ID><NAME>输液室</NAME><KZR_ID>11143</KZR_ID><KZR>李明南</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>29</ID><NAME>西药库</NAME><KZR_ID>13188</KZR_ID><KZR>徐飞群</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>30</ID><NAME>西药房</NAME><KZR_ID>13188</KZR_ID><KZR>徐飞群</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>31</ID><NAME>中药库</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>109</ID><NAME>体检中心</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>33</ID><NAME>院长室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>34</ID><NAME>书记室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>35</ID><NAME>副院长室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>36</ID><NAME>财务科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>37</ID><NAME>办公室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>38</ID><NAME>医务科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>39</ID><NAME>护理部</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>40</ID><NAME>信息中心</NAME><KZR_ID>999</KZR_ID><KZR>系统</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>41</ID><NAME>总务科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>42</ID><NAME>防保科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>43</ID><NAME>供应室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>44</ID><NAME>收费室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>45</ID><NAME>胜利社区</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>46</ID><NAME>总浦桥社区</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>49</ID><NAME>顺隆社区</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>50</ID><NAME>茗园社区</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>7</ID><NAME>其他科室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>1</ID><NAME>医务科室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>2</ID><NAME>医技科室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>3</ID><NAME>护理科室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>4</ID><NAME>药剂科室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>5</ID><NAME>行政科室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>62</ID><NAME>夜诊室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>67</ID><NAME>门卫</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>68</ID><NAME>餐厅</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>76</ID><NAME>义诊</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>59</ID><NAME>呼吸科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>60</ID><NAME>内</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>51</ID><NAME>电工房</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>52</ID><NAME>胃镜室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>53</ID><NAME>特约骨伤科</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>55</ID><NAME>一病区医办</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>56</ID><NAME>二病区医办</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>57</ID><NAME>B超室</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>58</ID><NAME>柯林公司</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>114</ID><NAME>内三病区医办</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>178</ID><NAME>隔离病区</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>185</ID><NAME>综合病区12</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>187</ID><NAME>EICU</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>179</ID><NAME>内分泌肾病</NAME><KZR_ID>13252</KZR_ID><KZR>丁佳</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>181</ID><NAME>金属园区6号</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>182</ID><NAME>金属园区7号</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>183</ID><NAME>综合病区11</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>184</ID><NAME>综合病房11</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>188</ID><NAME>老年医学科</NAME><KZR_ID>717076</KZR_ID><KZR>蔡珂丹</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>180</ID><NAME>综合病房12</NAME><KZR_ID/><KZR/></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>186</ID><NAME>神经内分泌肾</NAME><KZR_ID>11205</KZR_ID><KZR>张液丹</KZR></PUB_EMP_KSZR><PUB_EMP_KSZR><ID>189</ID><NAME>整形美容科</NAME><KZR_ID>13031</KZR_ID><KZR>黄鹄</KZR></PUB_EMP_KSZR>\n" +
                "</response>\n" +
                "</body>]]></won:result>\n" +
                "      </won:invokeResponse>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";

        String format = XmlUtil.format(respBody);
        System.out.println(format);

    }*/


}
