package com.example.model.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author banana
 * @create 2024-09-01 23:38
 */
@XmlRootElement(name = "body")
public class PubEmpReq {

    private Head head;

    private Request request;

    @XmlElement(name = "head")
    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    @XmlElement(name = "request")
    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }



    @XmlType(propOrder = {"userId", "passoword", "transNo"})
    public static class Head {

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


    public static class Request {
        private String branchCode;

        private String empCode;

        private String empId;

        private String empName;

        private Integer state;

        @XmlElement(name = "BRANCH_CODE")
        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        @XmlElement(name = "STATE")
        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        @XmlElement(name = "EMP_NAME")
        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        @XmlElement(name = "EMP_ID")
        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        @XmlElement(name = "EMP_CODE")
        public String getEmpCode() {
            return empCode;
        }

        public void setEmpCode(String empCode) {
            this.empCode = empCode;
        }
    }


}
