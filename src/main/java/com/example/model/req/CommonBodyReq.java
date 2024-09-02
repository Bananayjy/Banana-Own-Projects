package com.example.model.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author banana
 * @create 2024-09-01 23:39
 */
@XmlRootElement(name = "body")
public class CommonBodyReq {

    private CommonHeadReq head;

    private PubEmpReq request;

    @XmlElement(name = "head")
    public CommonHeadReq getHead() {
        return head;
    }

    public void setHead(CommonHeadReq head) {
        this.head = head;
    }

    @XmlElement(name = "request")
    public PubEmpReq getRequest() {
        return request;
    }

    public void setRequest(PubEmpReq request) {
        this.request = request;
    }
}
