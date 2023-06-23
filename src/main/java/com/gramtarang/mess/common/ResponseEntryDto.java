package com.gramtarang.mess.common;
// REVIEW PENDING

class ResponseEntryDto {
    private final long id;
    private final int index;
    private final String entryMessage;
    private final boolean actionRequired;

    public ResponseEntryDto(long id, int index, String message, boolean action) {
        this.id = id;
        this.index = index;
        this.entryMessage = message;
        this.actionRequired = action;
    }

    public long getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public String getEntryMessage() {
        return entryMessage;
    }

    public boolean isActionRequired() {
        return actionRequired;
    }

}
