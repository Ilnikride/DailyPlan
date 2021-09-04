package com.planing.day.core.messages.telegram.entities;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private Boolean is_bot;
    private String first_name;
    private String last_name;
    private  String username;
    private String language_code;
}
