package com.gramtarang.mess.entity.auditlog;

// Enumeration class for Audit Operation
public enum AuditOperation {
    CREATE(0, "Create"), MODIFY(1, "Modify"), LOAD(2, "Load"), DELETE(3, "Delete");

    private final int id;
    private final String name;

    AuditOperation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static AuditOperation valueOf(int id) {
        AuditOperation[] values = AuditOperation.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getId() == id)
                return values[i];
        }
        return CREATE;
    }

}
