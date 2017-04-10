package com.edx.omarhezi.chateamos.contacts.addcontact.events;

/**
 * Created by Omar Hezi on 07/04/17.
 */

public class AddContactEvent {
    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
