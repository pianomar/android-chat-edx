package com.edx.omarhezi.chateamos.chat;

import android.graphics.Bitmap;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Omar Hezi on 10/04/17.
 */

class ChatInteractorImpl implements com.edx.omarhezi.chateamos.chat.ChatInteractor {

    ChatRepository repository;

    public ChatInteractorImpl() {
        this.repository = new ChatRepositoryImpl();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        repository.changeConnectionStatus(online);
    }

    @Override
    public void sendMessage(String msg, String type) {
        repository.sendMessage(msg,type);
    }

    @Override
    public void setRecipient(String recipient) {
        repository.setRecipient(recipient);
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscrube();
    }

    @Override
    public void destroyListener() {
        repository.destroyListener();
    }

    @Override
    public void uploadImage(InputStream sasa) {
        repository.uploadImage(sasa);
    }
}
