package com.edx.omarhezi.chateamos.chat;

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
    public void sendMessage(String msg) {
        repository.sendMessage(msg);
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
}
