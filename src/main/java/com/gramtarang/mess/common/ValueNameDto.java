package com.gramtarang.mess.common;

public class ValueNameDto {
    private String value;
    private String name;

    public ValueNameDto() {
    }

    public ValueNameDto(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
