package com.edx.omarhezi.chateamos.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Omar Hezi on 10/04/17.
 */

@JsonIgnoreProperties({"sentByMe"})
public class ChatMessage {
    private String type;
    private String msg;
    private String sender;
    private boolean sentByMe;

    public ChatMessage() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if (obj instanceof User) {
            ChatMessage message = (ChatMessage) obj;
            equal = this.sender.equals(message.getSender()) &&
                    this.msg.equals(message.getMsg()) &&
                    this.sentByMe == message.sentByMe;
        }

        return equal;
    }
}
