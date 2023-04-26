package com.gramtarang.mess.common;

import org.springframework.data.annotation.Transient;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDto {

    @Transient
    private List<String> actions = new ArrayList<>();

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }
}
