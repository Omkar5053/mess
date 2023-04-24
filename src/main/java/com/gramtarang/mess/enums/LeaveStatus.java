package com.gramtarang.mess.enums;

public enum LeaveStatus {
    PENDING(0),
    APPROVED(1),
    REJECTED(2);

    private int id;
    LeaveStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
