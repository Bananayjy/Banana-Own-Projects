package com.example.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author banana
 * @create 2024-08-31 23:57
 */
@XmlType(propOrder = {"id", "name", "kzrId", "kzr"})
public class PubEmpKszr {
    private String id;
    private String name;
    private String kzrId;
    private String kzr;

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "KZR_ID")
    public String getKzrId() {
        return kzrId;
    }

    public void setKzrId(String kzrId) {
        this.kzrId = kzrId;
    }

    @XmlElement
    public String getKzr() {
        return kzr;
    }

    public void setKzr(String kzr) {
        this.kzr = kzr;
    }
}
