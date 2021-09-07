package com.planing.day.core.entities;

import lombok.Data;

import java.util.Date;

@Data
public class PlannedMessage {
    private String message;
    private Date sendDateTime;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(Date sendDateTime) {
        this.sendDateTime = sendDateTime;
    }
}
