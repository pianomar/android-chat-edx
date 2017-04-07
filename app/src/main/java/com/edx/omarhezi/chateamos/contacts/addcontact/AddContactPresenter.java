package com.edx.omarhezi.chateamos.contacts.addcontact;

import com.edx.omarhezi.chateamos.contacts.addcontact.events.AddContactEvent;

/**
 * Created by Lawrence Cermeño on 07/04/17.
 */

public interface AddContactPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}
