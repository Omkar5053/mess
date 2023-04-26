package com.gramtarang.mess.enums;

public enum RoleType {

    UNDEFINED(0),
    STUDENT(1),
    ADMIN(2),
    MESSINCHARGE(3),
    WARDEN(4),
    MENTOR(5),
    CHIEFWARDEN(6);

    private int id;
    RoleType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
