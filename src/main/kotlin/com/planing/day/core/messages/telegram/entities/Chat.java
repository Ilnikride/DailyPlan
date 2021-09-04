package com.planing.day.core.messages.telegram.entities;

import lombok.Data;

@Data
public class Chat {
    private Integer id;
    private String type;
    private String title;
    private String username;
    private String first_name;
    private String last_name;

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }
}
