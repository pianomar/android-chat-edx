package com.edx.omarhezi.chateamos.login;

import com.edx.omarhezi.chateamos.login.events.LoginEvent;

/**
 * Created by Omar Hezi on 05/04/17.
 */

public interface LoginPresenter {
    void onCreate();
    void onDestroy(); //To destroy it on view destroy

    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);
}
