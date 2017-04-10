package com.edx.omarhezi.chateamos.login;

/**
 * Created by Omar Hezi on 05/04/17.
 */

public interface LoginInteractor {
    void checkSession();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}
