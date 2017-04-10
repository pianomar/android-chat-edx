package com.edx.omarhezi.chateamos.contacts.addcontact.ui;

/**
 * Created by Omar Hezi on 07/04/17.
 */

public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void contactAdded();
    void contactNotAdded();
}
