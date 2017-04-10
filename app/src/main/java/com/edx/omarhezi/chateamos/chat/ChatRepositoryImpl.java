package com.edx.omarhezi.chateamos.chat;

import com.edx.omarhezi.chateamos.chat.events.ChatEvent;
import com.edx.omarhezi.chateamos.domain.FirebaseHelper;
import com.edx.omarhezi.chateamos.entities.ChatMessage;
import com.edx.omarhezi.chateamos.lib.EventBusInt;
import com.edx.omarhezi.chateamos.lib.EventBusIntImpl;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Omar Hezi on 10/04/17.
 */

class ChatRepositoryImpl implements ChatRepository {
    private String recipient;
    private EventBusIntImpl eventBus;
    private FirebaseHelper helper;
    private ChildEventListener childEventListener;

    public ChatRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        this.eventBus = EventBusIntImpl.getInstance();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }

    @Override
    public void sendMessage(String msg) {
        String keySender = helper.getAuthUserEmail();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(keySender);
        chatMessage.setMsg(msg);

        DatabaseReference chatsReference = helper.getChatsReference(recipient);
        chatsReference.push().setValue(chatMessage);
    }

    @Override
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public void subscribe() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                String msgSender = chatMessage.getSender();

                chatMessage.setSentByMe(msgSender.equals(helper.getAuthUserEmail()));

                ChatEvent chatEvent = new ChatEvent();
                chatEvent.setMessage(chatMessage);
                eventBus.post(chatEvent);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        helper.getChatsReference(recipient).addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscrube() {
        if(childEventListener!=null){
            helper.getChatsReference(recipient).removeEventListener(childEventListener);
        }
    }

    @Override
    public void destroyListener() {
        childEventListener = null;
    }
}
