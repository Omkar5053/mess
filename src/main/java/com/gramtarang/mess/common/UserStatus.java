package com.gramtarang.mess.common;

public enum UserStatus {
    UNDEFINED(0),
    ACTIVE(1),
    INACTIVE(2);

    private int id;

    UserStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public static UserStatus valueOf(int id) {
        switch (id) {
            case 1:
                return ACTIVE;
            case 2:
                return INACTIVE;
            default:
                return UNDEFINED;
        }
    }
}

