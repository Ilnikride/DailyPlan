package com.planing.day.core.messages.telegram.entities;

import lombok.Data;

@Data
public class Message {
    private Integer message_id;
    private User from;
    private Chat sender_chat;
    private Integer date;
    private Chat chat;
    private String text;

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public void setSender_chat(Chat sender_chat) {
        this.sender_chat = sender_chat;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Integer getMessage_id() {
        return message_id;
    }

    public User getFrom() {
        return from;
    }

    public Chat getSender_chat() {
        return sender_chat;
    }

    public Integer getDate() {
        return date;
    }

    public Chat getChat() {
        return chat;
    }
}
