package com.edx.omarhezi.chateamos.contacts;

import com.edx.omarhezi.chateamos.contacts.events.ContactListEvent;
import com.edx.omarhezi.chateamos.contacts.ui.ContactListView;
import com.edx.omarhezi.chateamos.entities.User;
import com.edx.omarhezi.chateamos.lib.EventBusInt;
import com.edx.omarhezi.chateamos.lib.EventBusIntImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Omar Hezi on 07/04/17.
 */

public class ContactListPresenterImpl implements ContactListPresenter{
    private EventBusIntImpl eventBus; // para detectar nos eventos
    private ContactListView contactListView; //para conectarme con la actividad
    private ContactsListInteractor contactsListInteractor;

    public ContactListPresenterImpl(ContactListView contactListView) {
        this.eventBus = EventBusIntImpl.getInstance();
        this.contactListView = contactListView;
        this.contactsListInteractor = new ContactsListInteractorImpl();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
        contactsListInteractor.subscribeToContactEvents();
    }

    @Override
    public void onDestroy() {
        eventBus.deregister(this);
        contactsListInteractor.destroyListener();
        contactListView = null;
    }

    @Override
    public void onPause() {
        contactsListInteractor.changeConnectionStatus(User.OFFLINE);
        contactsListInteractor.unsubscribeFromContactEvents();
    }

    @Override
    public void onResume() {
        contactsListInteractor.changeConnectionStatus(User.ONLINE);
        contactsListInteractor.subscribeToContactEvents();
    }

    @Override
    public void signOff() {
        contactsListInteractor.changeConnectionStatus(User.OFFLINE);
        contactsListInteractor.destroyListener();
        contactsListInteractor.unsubscribeFromContactEvents();
        contactsListInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return contactsListInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeContact(String email) {
        contactsListInteractor.removeContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent event) {
        //Manejar los posibles eventos que tengamos
        User user = event.getUser();
        switch (event.getEventType()){
            case ContactListEvent.onContactAdded:
                onContactAdded(user);
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;
            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;
        }
    }
    private void onContactAdded(User user){
        if(contactListView !=null){
            contactListView.onContactAdded(user);
        }
    }
    private void onContactChanged(User user){
        if(contactListView !=null){
            contactListView.onContactChanged(user);
        }
    }
    private void onContactRemoved(User user){
        if(contactListView !=null){
            contactListView.onContactRemoved(user);
        }
    }
}
