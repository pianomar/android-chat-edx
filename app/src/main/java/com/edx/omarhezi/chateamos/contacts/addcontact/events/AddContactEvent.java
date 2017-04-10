package com.edx.omarhezi.chateamos.contacts.addcontact.events;

/**
 * Created by Lawrence Cermeño on 07/04/17.
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
