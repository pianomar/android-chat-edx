package com.edx.omarhezi.chateamos.chat;

/**
 * Created by Lawrence Cermeño on 10/04/17.
 */

public interface ChatRepository {
    void changeConnectionStatus(boolean online);

    void sendMessage(String msg);
    void setRecipient(String recipient);

    void subscribe();
    void unsubscrube();
    void destroyListener();
}
