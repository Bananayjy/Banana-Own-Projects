package com.example.model.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author banana
 * @create 2024-09-01 23:39
 */
@XmlType(propOrder = {"userId", "passoword", "transNo"})
public class CommonHeadReq {

    private String userId;

    private String passoword;

    private String transNo;

    @XmlElement(name = "userid")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @XmlElement(name = "password")
    public String getPassoword() {
        return passoword;
    }

    public void setPassoword(String passoword) {
        this.passoword = passoword;
    }

    @XmlElement(name = "trans_no")
    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }
}
