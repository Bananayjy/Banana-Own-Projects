package com.example.model.resp;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author banana
 * @create 2024-09-02 0:25
 */
@XmlRootElement(name = "body")
public class PubEmpResp {

    private Response response;

    @XmlElement(name = "response")
    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        private Integer retCode;

        private String retInfo;

        private List<PubEmp> pubEmps;

        @XmlElement(name = "ret_code")
        public Integer getRetCode() {
            return retCode;
        }

        public void setRetCode(Integer retCode) {
            this.retCode = retCode;
        }

        @XmlElement(name = "ret_info")
        public String getRetInfo() {
            return retInfo;
        }

        public void setRetInfo(String retInfo) {
            this.retInfo = retInfo;
        }

        @XmlElement(name = "PUB_EMP")
        public List<PubEmp> getPubEmps() {
            return pubEmps;
        }

        public void setPubEmps(List<PubEmp> pubEmps) {
            this.pubEmps = pubEmps;
        }
    }

    public static class PubEmp {

        private String branchCode;

        private String empCode;

        private String empId;

        private String empName;

        private Integer state;

        private String deptName;

        private String deptCode;

        @XmlElement(name = "BRANCH_CODE")
        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        @XmlElement(name = "EMP_CODE")
        public String getEmpCode() {
            return empCode;
        }

        public void setEmpCode(String empCode) {
            this.empCode = empCode;
        }

        @XmlElement(name = "EMP_ID")
        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        @XmlElement(name = "EMP_NAME")
        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        @XmlElement(name = "STATE")
        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        @XmlElement(name = "DEPT_NAME")
        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        @XmlElement(name = "DEPT_CODE")
        public String getDeptCode() {
            return deptCode;
        }

        public void setDeptCode(String deptCode) {
            this.deptCode = deptCode;
        }
    }


}
