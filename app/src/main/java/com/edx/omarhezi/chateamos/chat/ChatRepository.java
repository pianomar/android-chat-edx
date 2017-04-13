package com.edx.omarhezi.chateamos.chat;

import android.graphics.Bitmap;

/**
 * Created by Omar Hezi on 10/04/17.
 */

public interface ChatRepository {
    void changeConnectionStatus(boolean online);

    void sendMessage(String msg, String type);
    void setRecipient(String recipient);

    void subscribe();
    void unsubscrube();
    void destroyListener();

    void uploadImage(Bitmap bitmap);
}
