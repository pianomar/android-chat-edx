package com.edx.omarhezi.chateamos.chat;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.edx.omarhezi.chateamos.R;
import com.edx.omarhezi.chateamos.chat.events.ChatEvent;
import com.edx.omarhezi.chateamos.chat.ui.ChatView;
import com.edx.omarhezi.chateamos.entities.ChatMessage;
import com.edx.omarhezi.chateamos.entities.ImageMessage;
import com.edx.omarhezi.chateamos.entities.TextMessage;
import com.edx.omarhezi.chateamos.entities.User;
import com.edx.omarhezi.chateamos.lib.EventBusIntImpl;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.InputStream;

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
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onCreate() {
        chatInteractor.subscribe();
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        chatInteractor.unsubscribe();
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
        ChatMessage msg;
        String messageType = event.getMessage().getType();
        String eventMessage = event.getMessage().getMsg();
        if(view !=null ){
            if (messageType.equals("text")){
                msg = new TextMessage(eventMessage);
            }
            else{
                msg = new ImageMessage(eventMessage);
            }

            msg.setSentByMe(event.getMessage().isSentByMe());
            view.onMessageReceived(msg);
        }
    }

    @Override
    public void uploadImage(InputStream stream) {
        chatInteractor.uploadImage(stream);
    }

}
