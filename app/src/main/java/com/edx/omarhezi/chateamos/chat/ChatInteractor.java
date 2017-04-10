package com.edx.omarhezi.chateamos.chat;

/**
 * Created by Lawrence Cermeño on 10/04/17.
 */

public interface ChatInteractor {
    void changeConnectionStatus();
    void sendMessage(String msg);
    void setRecipient(String msg);
    void subscribe();
    void unsubscrube();
    void destroyListener();
}
