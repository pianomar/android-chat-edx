package com.edx.omarhezi.chateamos.chat.events;

import com.edx.omarhezi.chateamos.entities.ChatMessage;

/**
 * Created by Lawrence Cermeño on 10/04/17.
 */

public class ChatEvent {
    private ChatMessage message;

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public ChatMessage getMessage() {
        return message;
    }
}
