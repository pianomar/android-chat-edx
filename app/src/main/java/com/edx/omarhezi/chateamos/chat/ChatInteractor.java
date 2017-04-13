package com.edx.omarhezi.chateamos.chat;

import android.graphics.Bitmap;

/**
 * Created by Omar Hezi on 10/04/17.
 */

public interface ChatInteractor {
    void changeConnectionStatus(boolean bool);
    void sendMessage(String msg, String type);
    void setRecipient(String recipient);
    void subscribe();
    void unsubscribe();
    void destroyListener();

    void uploadImage(Bitmap bitmap);
}
