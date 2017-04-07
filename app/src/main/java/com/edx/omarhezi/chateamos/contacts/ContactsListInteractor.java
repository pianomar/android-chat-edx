package com.edx.omarhezi.chateamos.contacts;

/**
 * Created by Lawrence Cermeño on 06/04/17.
 */

public interface ContactsListInteractor {
    void subscribeToContactEvents();
    void unsubscribeFromContactEvents();
    void destroyListener();
    void removeContact(String email);

    //Actions for sessions
    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean online);

}
