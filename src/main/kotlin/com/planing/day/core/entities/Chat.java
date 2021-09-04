package com.planing.day.core.entities;

import java.util.Date;


public class Chat {

    private Integer id;
    private String userName;
    private Date sendDateTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSendDateTime(Date sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Date getSendDateTime() {
        return sendDateTime;
    }

}
