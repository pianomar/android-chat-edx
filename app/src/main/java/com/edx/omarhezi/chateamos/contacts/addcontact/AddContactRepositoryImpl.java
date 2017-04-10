package com.edx.omarhezi.chateamos.contacts.addcontact;

import com.edx.omarhezi.chateamos.contacts.addcontact.events.AddContactEvent;
import com.edx.omarhezi.chateamos.domain.FirebaseHelper;
import com.edx.omarhezi.chateamos.entities.User;
import com.edx.omarhezi.chateamos.lib.EventBusIntImpl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Omar Hezi on 10/04/17.
 */

class AddContactRepositoryImpl implements AddContactRepository {
    private EventBusIntImpl eventBus;
    private FirebaseHelper helper;

    public AddContactRepositoryImpl() {
        this.eventBus = EventBusIntImpl.getInstance();
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void addContact(String email) {
        final String key = email.replace(".","_");
        DatabaseReference userReference = helper.getUserReference(key);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() { //THIS READS DATA ONCE
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user !=null){
                    DatabaseReference myContactReference = helper.getMyContactsReference();
                    myContactReference.child(key).setValue(user.isOnline());
                    String curretUserKey = helper.getAuthUserEmail();
                    curretUserKey = curretUserKey.replace(".","_");
                    DatabaseReference reverseContactReference = helper.getContactsReference(key);
                    reverseContactReference.child(curretUserKey).setValue(User.ONLINE);
                    postSuccess();
                }else{
                    postError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                postError();
            }
        });
    }

    private void postError(){
        post(true);
    }

    private void postSuccess(){
        post(false);
    }

    private void post(boolean error) {
        AddContactEvent event = new AddContactEvent();
        event.setError(error);
        eventBus.post(event);
    }
}
