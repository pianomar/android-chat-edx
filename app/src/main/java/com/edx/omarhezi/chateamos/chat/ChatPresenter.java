package com.edx.omarhezi.chateamos.chat;

import android.graphics.Bitmap;

import com.edx.omarhezi.chateamos.chat.events.ChatEvent;

/**
 * Created by Omar Hezi on 10/04/17.
 */

public interface ChatPresenter {
    void onPause();
    void onResume();

    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);
    void sendMessage(String msg,String type);
    void onEventMainThread(ChatEvent event);

    void uploadImage(Bitmap bitmap);
}
