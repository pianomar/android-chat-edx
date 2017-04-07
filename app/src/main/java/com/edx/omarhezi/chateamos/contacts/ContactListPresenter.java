package com.edx.omarhezi.chateamos.contacts;

import com.edx.omarhezi.chateamos.contacts.events.ContactListEvent;

/**
 * Created by Lawrence Cermeño on 06/04/17.
 */

public interface ContactListPresenter {
    void onCreate();
    void onDestroy();

    void onPause();
    void onResume();

    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void onEventMainThread(ContactListEvent event);

    //void getContacts();
}
