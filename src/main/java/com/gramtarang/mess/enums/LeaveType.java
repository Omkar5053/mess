package com.gramtarang.mess.enums;

public enum LeaveType {
    FORMAL(0),
    OUTING(1);
    private int id;
    LeaveType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
