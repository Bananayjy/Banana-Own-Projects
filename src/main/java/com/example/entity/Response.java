package com.example.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author banana
 * @create 2024-08-31 23:57
 */
@XmlType(propOrder = {"retCode", "retInfo", "pubEmpKszrList"})
public class Response {
    private String retCode;
    private String retInfo;
    private List<PubEmpKszr> pubEmpKszrList;

    @XmlElement(name = "ret_code")
    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    @XmlElement(name = "ret_info")
    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    @XmlElement(name = "PUB_EMP_KSZR")
    public List<PubEmpKszr> getPubEmpKszrList() {
        return pubEmpKszrList;
    }

    public void setPubEmpKszrList(List<PubEmpKszr> pubEmpKszrList) {
        this.pubEmpKszrList = pubEmpKszrList;
    }
}
