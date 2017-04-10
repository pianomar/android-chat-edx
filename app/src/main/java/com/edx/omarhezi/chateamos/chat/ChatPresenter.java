package com.edx.omarhezi.chateamos.chat;

import com.edx.omarhezi.chateamos.chat.events.ChatEvent;

/**
 * Created by Lawrence Cermeño on 10/04/17.
 */

public interface ChatPresenter {
    void onPause();
    void onResume();

    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);
}
