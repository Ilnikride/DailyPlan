package com.planing.day.core.messages.telegram.entities;

import lombok.Data;

@Data
public class Update {
    private Integer update_id;

    private Message message;

    public Integer getUpdate_id() {
        return update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setUpdate_id(Integer update_id) {
        this.update_id = update_id;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
