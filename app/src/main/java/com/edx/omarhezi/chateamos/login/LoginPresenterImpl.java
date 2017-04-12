package com.edx.omarhezi.chateamos.login;

import com.edx.omarhezi.chateamos.lib.EventBusIntImpl;
import com.edx.omarhezi.chateamos.login.events.LoginEvent;
import com.edx.omarhezi.chateamos.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Omar Hezi on 05/04/17.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private EventBusIntImpl eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.eventBus = EventBusIntImpl.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.deregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgressBar();
        }

        loginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgressBar();
        }

        loginInteractor.doSignIn(email, password);

    }

    @Override
    public void registerNewUser(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgressBar();
        }

        loginInteractor.doSignUp(email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;

        }
    }

    private void onFailedToRecoverSession() {
        if (loginView != null) {
            loginView.enableInputs();
            loginView.hideProgressBar();
        }
    }

    private void onSignInSuccess() {
        if (loginView != null) {
            loginView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess() {
        if (loginView != null) {
            loginView.newUserSuccess();
        }
    }

    private void onSignInError(String error) {
        if (loginView != null) {
            loginView.enableInputs();
            loginView.hideProgressBar();
            loginView.loginError(error);
        }
    }

    private void onSignUpError(String error) {
        if (loginView != null) {
            loginView.enableInputs();
            loginView.hideProgressBar();
            loginView.newUserError(error);
        }
    }
}
