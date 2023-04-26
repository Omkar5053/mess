package com.gramtarang.mess.entity.auditlog;

// Enumeration class for Audit trail status
public enum Status {
    SUCCESS(0, "Success"), FAIL(1, "Fail");

    private final int id;
    private final String name;

    Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Status valueOf(int id) {
        Status[] values = Status.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getId() == id)
                return values[i];
        }
        return FAIL;
    }
}
