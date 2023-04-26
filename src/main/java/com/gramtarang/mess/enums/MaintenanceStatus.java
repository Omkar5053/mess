package com.gramtarang.mess.enums;

public enum MaintenanceStatus {
    UNDEFINED(0),
    UNASSIGNED(1),
    ASSIGNED(2),
    COMPLETED(3),
    CLOSED(4);

    private int id;
    MaintenanceStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}

