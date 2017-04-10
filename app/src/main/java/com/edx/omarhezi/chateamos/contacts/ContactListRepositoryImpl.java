package com.edx.omarhezi.chateamos.contacts;

import com.edx.omarhezi.chateamos.contacts.events.ContactListEvent;
import com.edx.omarhezi.chateamos.domain.FirebaseHelper;
import com.edx.omarhezi.chateamos.entities.User;
import com.edx.omarhezi.chateamos.lib.EventBusIntImpl;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Omar Hezi on 07/04/17.
 */

class ContactListRepositoryImpl implements ContactListRepository {

    private FirebaseHelper firebaseHelper;
    private ChildEventListener contactChildtEventListener;
    private EventBusIntImpl eventBus;

    public ContactListRepositoryImpl(){
        this.firebaseHelper = FirebaseHelper.getInstance();
        this.eventBus = EventBusIntImpl.getInstance();
    }

    @Override
    public void signOff() {
        firebaseHelper.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return firebaseHelper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail = firebaseHelper.getAuthUserEmail();
        firebaseHelper.getOneContactReference(currentUserEmail, email).removeValue();
        firebaseHelper.getOneContactReference(email, currentUserEmail).removeValue();
    }

    @Override
    public void subscribeToContactListEvents() {

        if(contactChildtEventListener == null){
            contactChildtEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.onContactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot, ContactListEvent.onContactRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
        }
        firebaseHelper.getMyContactsReference().addChildEventListener(contactChildtEventListener);
    }

    private void post(int type, User user) {
        ContactListEvent event = new ContactListEvent();
        event.setEventType(type);
        event.setUser(user);
        eventBus.post(event);
    }

    @Override
    public void unsubscribeFromContactListEvents() {
        if(contactChildtEventListener!=null){
            firebaseHelper.getMyContactsReference().removeEventListener(contactChildtEventListener);
        }
    }

    @Override
    public void destroyListener() {
        contactChildtEventListener = null;
    }

    @Override
    public void changeConnectionStatus(boolean online) {

    }

    public void handleContact(DataSnapshot dataSnapshot, int eventType){
        String email = dataSnapshot.getKey();
        email = email.replace("_",".");
        boolean online = ((Boolean) dataSnapshot.getValue()).booleanValue();
        User user = new User();
        user.setEmail(email);
        user.setOnline(online);
        post(eventType, user);
    }
}
