package com.edx.omarhezi.chateamos.login.events;

/**
 * Created by Omar Hezi on 05/04/17.
 */

public class LoginEvent {
    public final static int onSignInError = 0;
    public final static int onSignUpError = 2;
    public final static int onSignInSuccess = 3;
    public final static int onSignUpSuccess = 4;
    public final static int onFailedToRecoverSession = 5;

    private int eventType;
    private String errorMessage;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
