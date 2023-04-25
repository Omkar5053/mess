package com.gramtarang.mess.enums;

public enum UserType {

    UNDEFINED(0),
    DAYSCHOLAR(1),
    HOSTELLER(2);

    private int id;

    UserType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
