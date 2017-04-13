package com.edx.omarhezi.chateamos.chat;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.edx.omarhezi.chateamos.chat.events.ChatEvent;
import com.edx.omarhezi.chateamos.domain.FirebaseHelper;
import com.edx.omarhezi.chateamos.entities.ChatMessage;
import com.edx.omarhezi.chateamos.lib.EventBusInt;
import com.edx.omarhezi.chateamos.lib.EventBusIntImpl;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

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
    public void sendMessage(String msg, String type) {
        String keySender = helper.getAuthUserEmail();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(keySender);
        chatMessage.setMsg(msg);
        chatMessage.setType(type);

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

    @Override
    public void uploadImage(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = helper.getStorageRef().putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(this.getClass().getSimpleName(), e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            @SuppressWarnings("VisibleForTests")
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sendMessage(taskSnapshot.getDownloadUrl().toString(), "image");
            }
        });

    }


}
