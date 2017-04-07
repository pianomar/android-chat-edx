package com.edx.omarhezi.chateamos.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.edx.omarhezi.chateamos.domain.FirebaseHelper;
import com.edx.omarhezi.chateamos.entities.User;
import com.edx.omarhezi.chateamos.lib.EventBusInt;
import com.edx.omarhezi.chateamos.lib.EventBusIntImpl;
import com.edx.omarhezi.chateamos.login.events.LoginEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Lawrence Cermeño on 05/04/17.
 */

public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseHelper helper;
    private DatabaseReference databaseReference;
    private DatabaseReference myUserReference;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public LoginRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        this.databaseReference = helper.getDataReference();
        this.myUserReference = helper.getMyUserReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signUp(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            postEvent(LoginEvent.onSignUpSuccess);
                            signIn(email, password);
                        } else {
                            postEvent(LoginEvent.onSignUpError, task.getException().toString());
                        }
                    }
                });
    }

    @Override
    public void signIn(String email, String password) {

        if (password.equals("") || email.equals("")) {
            postEvent(LoginEvent.onSignInError);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myUserReference = helper.getMyUserReference();

                            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User currentUser = dataSnapshot.getValue(User.class);
//                                    if (currentUser == null) {
//                                        String email = helper.getAuthUserEmail();
//                                        if (email != null) {
//                                            currentUser = new User();
//                                            myUserReference.setValue(currentUser);
//                                        }
//                                    }
                                    helper.changeUserConnectionStatus(User.ONLINE);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        } else {
                            postEvent(LoginEvent.onSignInError);
                        }
                    }
                });
    }


    @Override
    public void checkSession() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    postEvent(LoginEvent.onSignInSuccess);
                    Log.e(this.getClass().getName(), "Signed in");
                } else {
                    postEvent(LoginEvent.onFailedToRecoverSession);
                    Log.e(this.getClass().getName(), "Signed out");
                }

            }
        };

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBusInt eventBusInt = new EventBusIntImpl();
        eventBusInt.post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }
}
