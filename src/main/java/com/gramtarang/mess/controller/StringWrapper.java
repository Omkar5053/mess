package com.gramtarang.mess.controller;
// REVIEW PENDING


import com.gramtarang.mess.common.ResponseDto;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StringWrapper extends ResponseDto {
    private String content;

    public StringWrapper() {

    }

    public StringWrapper(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
