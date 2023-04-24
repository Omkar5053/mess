package com.gramtarang.mess.enums;

public enum MaintenanceType {
    ELECTRICAL(0),
    CIVIL(1);

    private int id;
    MaintenanceType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
