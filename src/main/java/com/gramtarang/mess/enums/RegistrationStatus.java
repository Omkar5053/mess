package com.gramtarang.mess.enums;

public enum RegistrationStatus {

    REGISTRED(1),
    NOTREGISTRED(2);

    private int id;
    RegistrationStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public static RegistrationStatus valueOf(int id) {
        switch (id) {
            case 1:
                return REGISTRED;
            case 2:
                return NOTREGISTRED;
            default:
                return NOTREGISTRED;
        }
    }

}
