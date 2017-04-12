package com.edx.omarhezi.chateamos.chat;
import com.edx.omarhezi.chateamos.chat.events.ChatEvent;
import com.edx.omarhezi.chateamos.chat.ui.ChatView;
import com.edx.omarhezi.chateamos.entities.User;
import com.edx.omarhezi.chateamos.lib.EventBusIntImpl;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Omar Hezi on 10/04/17.
 */

public class ChatPresenterImpl implements ChatPresenter {
    private EventBusIntImpl eventBus;
    private ChatView view;
    private ChatInteractor chatInteractor;

    public ChatPresenterImpl(ChatView view) {
        this.view = view;
        this.eventBus = EventBusIntImpl.getInstance();
        this.chatInteractor = new ChatInteractorImpl();
    }

    @Override
    public void onPause() {
        chatInteractor.unsubscribe();
    }

    @Override
    public void onResume() {
        chatInteractor.subscribe();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.deregister(this);
        chatInteractor.destroyListener();
        view = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        chatInteractor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatInteractor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if(view !=null){
            view.onMessageReceived(event.getMessage());
        }
    }
}
