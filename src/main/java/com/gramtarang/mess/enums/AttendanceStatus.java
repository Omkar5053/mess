package com.gramtarang.mess.enums;

public enum AttendanceStatus {
    PRESENT(0),
    ABSENT(1);

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
}
