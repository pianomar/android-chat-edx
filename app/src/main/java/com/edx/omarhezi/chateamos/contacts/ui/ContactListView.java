package com.edx.omarhezi.chateamos.contacts.ui;

import com.edx.omarhezi.chateamos.entities.User;

/**
 * Created by Lawrence Cermeño on 06/04/17.
 */

public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
