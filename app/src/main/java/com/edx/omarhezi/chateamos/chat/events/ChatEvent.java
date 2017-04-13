package com.edx.omarhezi.chateamos.chat.events;

import android.media.Image;

import com.edx.omarhezi.chateamos.entities.ChatMessage;

/**
 * Created by Omar Hezi on 10/04/17.
 */

public class ChatEvent {
    private ChatMessage message;
    private int eventType;
    public final static int IMAGE = 0;
    public final static int TEXT = 2;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public ChatMessage getMessage() {
        return message;
    }
}
