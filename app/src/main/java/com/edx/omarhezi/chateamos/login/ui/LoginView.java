package com.edx.omarhezi.chateamos.login.ui;

/**
 * Created by Omar Hezi on 05/04/17.
 */

public interface LoginView {
    //Notice all methods are showing or hiding things
    void enableInputs();
    void disableInputs();
    void showProgressBar();
    void hideProgressBar();

    void handleSignUp();
    void handleSignIn();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);
}
