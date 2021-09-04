package com.planing.day.core.messages.telegram.entities;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public class UpdateResponse {
    private Boolean ok;
    private List<Update> result;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<Update> getResult() {
        return result;
    }

    public void setResult(List<Update> result) {
        this.result = result;
    }
}
