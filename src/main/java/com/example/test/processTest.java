package com.example.test;

import com.example.convert.SoapConvert;
import com.example.core.SoapClient;
import com.example.enums.SoapProtocol;
import com.example.model.req.PubEmpReq;
import com.example.model.resp.PubEmpResp;
import com.example.utils.SoapUtil;

/**
 * @author banana
 * @create 2024-09-01 23:37
 */
public class processTest {

    // 正常请求流程 -> 响应解析流程
    public static void main(String[] args) {

        PubEmpReq pubEmpReq = new PubEmpReq();
        PubEmpReq.Head head = new PubEmpReq.Head();
        head.setUserId("123");
        head.setPassoword("66666");
        head.setTransNo("555");
        pubEmpReq.setHead(head);

        PubEmpReq.Request request = new PubEmpReq.Request();
        request.setEmpId("123");
        request.setEmpCode("001");
        request.setBranchCode("00");
        request.setEmpName("yjy");
        pubEmpReq.setRequest(request);

        String s = SoapConvert.ObjectToXml(pubEmpReq, PubEmpReq.class, true);
        System.out.println(s);

        SoapClient clientKt = SoapUtil.createClientKt("123", SoapProtocol.SOAP_1_2, s);
        System.out.println(clientKt.getMsgStr(true));

        // String resp = clientKt.send(true);

        // =======================================

        String resp = "<soap:Envelope\n" +
                "    xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "    <soap:Body>\n" +
                "        <won:invokeResponse\n" +
                "            xmlns:won=\"www.WondersHSBP.com\">\n" +
                "            <!--type: string-->\n" +
                "            <won:result>\n" +
                "                <![CDATA[<body>\n" +
                "    <response>\n" +
                "        <ret_code>0</ret_code>\n" +
                "        <ret_info>success</ret_info>\n" +
                "        <PUB_EMP>\n" +
                "            <BRANCH_CODE>00</BRANCH_CODE>\n" +
                "            <EMP_ID>15025</EMP_ID>\n" +
                "            <EMP_CODE>15025</EMP_CODE>\n" +
                "            <EMP_NAME>邵健</EMP_NAME>\n" +
                "            <SEX>9</SEX>\n" +
                "            <ID_CARD>未填写</ID_CARD>\n" +
                "            <MOBILE_PHONE>13857477190</MOBILE_PHONE>\n" +
                "            <WORK_PHONE/>\n" +
                "            <DATE_OF_BIRTH/>\n" +
                "            <DEPT_CODE>040</DEPT_CODE>\n" +
                "            <DEPT_NAME>信息中心</DEPT_NAME>\n" +
                "            <TITLES_ID>11</TITLES_ID>\n" +
                "            <TITLES_NAME>无</TITLES_NAME>\n" +
                "            <GRADE_ID>0</GRADE_ID>\n" +
                "            <GRADE_NAME>无</GRADE_NAME>\n" +
                "            <INPUTCODE1>SJ</INPUTCODE1>\n" +
                "            <INPUTCODE2>VW</INPUTCODE2>\n" +
                "            <EMP_TYPE>4,9,2,3,1,10,99,6,5,7,8</EMP_TYPE>\n" +
                "            <EMP_TYPE_NAME>医技人员,后勤人员,诊疗人员,护理人员,行政人员 ,护工人员,其他人员,麻醉人员,手术人员,药事人员,财务人员</EMP_TYPE_NAME>\n" +
                "            <WARD_ID>16</WARD_ID>\n" +
                "            <PASSWORD>b5e2698c8fc4079641af5e8225902fbc</PASSWORD>\n" +
                "            <JOB_TITLE_ID>27</JOB_TITLE_ID>\n" +
                "            <DESCRIPTION/>\n" +
                "            <STATE>1</STATE>\n" +
                "            <FAMILY_PHONE/>\n" +
                "            <MODIFY_EMPID>999</MODIFY_EMPID>\n" +
                "            <MODIFY_TIME>2023-03-28 15:44:36</MODIFY_TIME>\n" +
                "            <PROFILE/>\n" +
                "            <PHOTO_INDEX/>\n" +
                "            <HOME_ADDR/>\n" +
                "            <QQ/>\n" +
                "            <LICENSE_NO/>\n" +
                "            <LICENSE_RANGE/>\n" +
                "            <MAIN_WORK_GROUP>0</MAIN_WORK_GROUP>\n" +
                "            <ID_CARD_TYPE>1</ID_CARD_TYPE>\n" +
                "        </PUB_EMP>\n" +
                "        <PUB_EMP>\n" +
                "            <BRANCH_CODE>00</BRANCH_CODE>\n" +
                "            <EMP_ID>11056</EMP_ID>\n" +
                "            <EMP_CODE>11056</EMP_CODE>\n" +
                "            <EMP_NAME>李清林</EMP_NAME>\n" +
                "            <SEX>1</SEX>\n" +
                "            <ID_CARD>230223197001243259</ID_CARD>\n" +
                "            <MOBILE_PHONE>13989357501</MOBILE_PHONE>\n" +
                "            <WORK_PHONE/>\n" +
                "            <DATE_OF_BIRTH>1964-03-26 00:00:00</DATE_OF_BIRTH>\n" +
                "            <DEPT_CODE>019</DEPT_CODE>\n" +
                "            <DEPT_NAME>耳鼻咽喉科</DEPT_NAME>\n" +
                "            <TITLES_ID>2</TITLES_ID>\n" +
                "            <TITLES_NAME>副主任医师</TITLES_NAME>\n" +
                "            <GRADE_ID>30</GRADE_ID>\n" +
                "            <GRADE_NAME>副高</GRADE_NAME>\n" +
                "            <INPUTCODE1>LQL</INPUTCODE1>\n" +
                "            <INPUTCODE2>SIS</INPUTCODE2>\n" +
                "            <EMP_TYPE>2</EMP_TYPE>\n" +
                "            <EMP_TYPE_NAME>诊疗人员</EMP_TYPE_NAME>\n" +
                "            <WARD_ID>6</WARD_ID>\n" +
                "            <PASSWORD>647dc5288b8ff4a51c26a54cde05cb2b</PASSWORD>\n" +
                "            <JOB_TITLE_ID>27</JOB_TITLE_ID>\n" +
                "            <DESCRIPTION/>\n" +
                "            <STATE>1</STATE>\n" +
                "            <FAMILY_PHONE/>\n" +
                "            <MODIFY_EMPID>999</MODIFY_EMPID>\n" +
                "            <MODIFY_TIME>2023-07-26 09:07:40</MODIFY_TIME>\n" +
                "            <PROFILE/>\n" +
                "            <PHOTO_INDEX/>\n" +
                "            <HOME_ADDR/>\n" +
                "            <QQ/>\n" +
                "            <LICENSE_NO>110330211001283</LICENSE_NO>\n" +
                "            <LICENSE_RANGE>眼耳鼻咽喉专科</LICENSE_RANGE>\n" +
                "            <MAIN_WORK_GROUP>0</MAIN_WORK_GROUP>\n" +
                "            <ID_CARD_TYPE>1</ID_CARD_TYPE>\n" +
                "        </PUB_EMP></response></body>]]>\n" +
                "            </won:result>\n" +
                "        </won:invokeResponse>\n" +
                "    </soap:Body>\n" +
                "</soap:Envelope>";


        String result =
        SoapUtil.getSoapElementByTagNameNS(resp, SoapProtocol.SOAP_1_2, "www.WondersHSBP.com", "result");
        PubEmpResp pubEmpResp = SoapConvert.XmlToObject(result, PubEmpResp.class);
        System.out.println(pubEmpResp);


    }

}
