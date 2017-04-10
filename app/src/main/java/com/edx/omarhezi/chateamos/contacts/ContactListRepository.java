package com.edx.omarhezi.chateamos.contacts;

/**
 * Created by Omar Hezi on 06/04/17.
 */

public interface ContactListRepository {
    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void subscribeToContactListEvents();
    void unsubscribeFromContactListEvents();
    void destroyListener();
    void changeConnectionStatus(boolean online);
}
