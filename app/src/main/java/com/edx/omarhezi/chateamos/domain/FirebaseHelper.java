package com.edx.omarhezi.chateamos.domain;

import com.edx.omarhezi.chateamos.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Omar Hezi on 04/04/17.
 */

public class FirebaseHelper {
    private DatabaseReference dataReference;
    private final static String SEPARATOR = "____";
    private final static String USERS_PATH = "users";
    private final static String CHATS_PATH = "chats";
    private final static String CONTACTS_PATH = "contacts";
    private final static String FIREBASE_URL = "https://chatfirebase-11a70.firebaseio.com/";

    //The method that has an instance of the object of the class.
    private static class SingletonHolder{
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    //My singleton, which returns an instance ^
    public static FirebaseHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper(){
        dataReference = FirebaseDatabase.getInstance().getReferenceFromUrl(FIREBASE_URL);
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    //Get the authenticated user email at the moment, can be called if user hasn't create a
    //session yet
    public String getAuthUserEmail(){
        String email=null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null){
            email = user.getEmail();
        }
        return email;
    }

    public DatabaseReference getUserReference(String email){
        DatabaseReference userReference = null;
        if(email != null){
            String emailKey = email.replace(".","_");
            userReference = dataReference.getRoot().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }

    //To access MY user reference
    public DatabaseReference getMyUserReference(){
        return getUserReference(getAuthUserEmail());
    }

    public DatabaseReference getContactsReference(String email){
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public DatabaseReference getMyContactsReference(){
        return getContactsReference(getAuthUserEmail());
    }

    public DatabaseReference getOneContactReference(String mainEmail, String childEmail){
        String childKey = childEmail.replace(".","_");
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }

    public DatabaseReference getChatsReference(String receiver){
        String keySender = getAuthUserEmail().replace(".","_");
        String keyReceiver = receiver.replace(".","_");

        String keyChat = keySender + SEPARATOR + keyReceiver;
        if(keySender.compareTo(keyReceiver)>0){
            keyChat = keyReceiver + SEPARATOR + keySender;
        }
        return dataReference.getRoot().child(CHATS_PATH).child(keyChat);
    }

    public void changeUserConnectionStatus(boolean online){
        if(getMyUserReference()!=null){
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("online",online);
            getMyUserReference().updateChildren(updates);
            notifyContactsOfConnectionChange(online);
        }
    }

    public void notifyContactsOfConnectionChange(boolean online){
        notifyContactsOfConnectionChange(online, false);
    }

    public void signOff(){
        //I have made an edit
        notifyContactsOfConnectionChange(User.OFFLINE,true);
        FirebaseAuth.getInstance().signOut();
    }

    public void notifyContactsOfConnectionChange(final boolean online, final boolean signoff){
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    String email = child.getKey();
                    DatabaseReference reference = getOneContactReference(email, myEmail);
                    reference.setValue(online);
                }
                if(signoff){
                    FirebaseAuth.getInstance().signOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
