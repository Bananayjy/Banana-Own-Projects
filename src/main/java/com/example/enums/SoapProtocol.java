package com.example.enums;

import cn.hutool.socket.protocol.Protocol;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SoapProtocol {

    SOAP_1_1("http://schemas.xmlsoap.org/soap/envelope", "SOAP 1.1 Protocol"),

    SOAP_1_2("http://schemas.xmlsoap.org/soap/encoding", "SOAP 1.2 Protocol");

    private final String value;

    private final String desc;

}