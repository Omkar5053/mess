package com.gramtarang.mess.enums;

public enum FoodType {
    VEG(0),
    NON_VEG(1);

    private int id;
    FoodType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
