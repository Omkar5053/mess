package com.gramtarang.mess.enums;

public enum AmbulanceStatus {
    SUBMITTED(0),
    APPROVED(1);

    private int id;

    AmbulanceStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
