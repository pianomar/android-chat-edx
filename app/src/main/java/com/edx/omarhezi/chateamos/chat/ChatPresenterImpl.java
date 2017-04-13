package com.edx.omarhezi.chateamos.chat;
import android.graphics.Bitmap;

import com.edx.omarhezi.chateamos.chat.events.ChatEvent;
import com.edx.omarhezi.chateamos.chat.ui.ChatView;
import com.edx.omarhezi.chateamos.entities.ImageMessage;
import com.edx.omarhezi.chateamos.entities.TextMessage;
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
    public void sendMessage(String msg,String type) {
        chatInteractor.sendMessage(msg,type);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if(view !=null ){
            if (event.getMessage().getType().equals("text")){
                TextMessage txtMessage = new TextMessage(event.getMessage().getMsg());
                view.onMessageReceived(txtMessage);
            }
            else{
                ImageMessage imgMessage = new ImageMessage(event.getMessage().getMsg());
                view.onMessageReceived(imgMessage);
            }
        }
    }

    @Override
    public void uploadImage(Bitmap bitmap) {
        chatInteractor.uploadImage(bitmap);
    }
}
