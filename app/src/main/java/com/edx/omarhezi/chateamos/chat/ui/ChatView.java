package com.edx.omarhezi.chateamos.chat.ui;

import com.edx.omarhezi.chateamos.entities.ChatMessage;

/**
 * Created by Lawrence Cermeño on 10/04/17.
 */

public interface ChatView {
    void onMessageSent(String message);
    void onMessageReceived(ChatMessage message);
}
