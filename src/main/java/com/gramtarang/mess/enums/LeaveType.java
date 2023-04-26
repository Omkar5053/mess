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

    public static LeaveType valueOf(int id) {
        switch (id) {
            case 0:
                return FORMAL;
            case 1:
                return OUTING;
            default:
                return FORMAL;
        }
    }
}
