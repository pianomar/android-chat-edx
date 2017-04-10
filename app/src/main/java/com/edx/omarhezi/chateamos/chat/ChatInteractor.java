package com.edx.omarhezi.chateamos.chat;

/**
 * Created by Omar Hezi on 10/04/17.
 */

public interface ChatInteractor {
    void changeConnectionStatus(boolean bool);
    void sendMessage(String msg);
    void setRecipient(String recipient);
    void subscribe();
    void unsubscribe();
    void destroyListener();
}
