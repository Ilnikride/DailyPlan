package com.planing.day.core.entities;

import lombok.Data;

@Data
public class Chat {

    private Integer id;
    private String userName;
    private PlannedMessage plannedMessage;

    public PlannedMessage getPlannedMessage() {
        return plannedMessage;
    }

    public void setPlannedMessage(PlannedMessage plannedMessage) {
        this.plannedMessage = plannedMessage;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

}
