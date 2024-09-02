package com.example.model.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author banana
 * @create 2024-09-01 18:30
 */

public class DeptPeople {



    private List<dept> pubEmpKszr;

    @XmlElement(name = "PUB_EMP_KSZR")
    public List<dept> getPubEmpKszr() {
        return pubEmpKszr;
    }

    public void setPubEmpKszr(List<dept> pubEmpKszr) {
        this.pubEmpKszr = pubEmpKszr;
    }

    @XmlType(propOrder = {"id", "name", "kzrId", "kzrName"})
    static class dept {

        private String id;

        private String name;

        private String kzrId;

        private String kzrName;

        @XmlElement(name = "ID")
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @XmlElement(name = "KZR")
        public String getKzrName() {
            return kzrName;
        }

        public void setKzrName(String kzrName) {
            this.kzrName = kzrName;
        }

        @XmlElement(name = "KZR_ID")
        public String getKzrId() {
            return kzrId;
        }

        public void setKzrId(String kzrId) {
            this.kzrId = kzrId;
        }

        @XmlElement(name = "NAME")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
