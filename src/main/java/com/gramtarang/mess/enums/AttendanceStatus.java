package com.gramtarang.mess.enums;

import com.gramtarang.mess.common.UserStatus;

public enum AttendanceStatus {
    ABSENT(0),
    PRESENT(1);


    private int id;

    AttendanceStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static AttendanceStatus valueOf(int id) {
        switch (id) {
            case 0:
                return ABSENT;
            case 1:
                return PRESENT;
            default:
                return ABSENT;
        }
    }
}
