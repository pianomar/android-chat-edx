package com.edx.omarhezi.chateamos.contacts.addcontact;

import com.edx.omarhezi.chateamos.contacts.addcontact.events.AddContactEvent;
import com.edx.omarhezi.chateamos.contacts.addcontact.ui.AddContactView;
import com.edx.omarhezi.chateamos.lib.EventBusIntImpl;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Lawrence Cermeño on 10/04/17.
 */

public class AddContactPresenterImpl implements AddContactPresenter {
    private EventBusIntImpl eventBus;
    private AddContactView view;
    private AddContactInteractor interactor;

    public AddContactPresenterImpl(AddContactView view) {
        this.view = view;
        this.eventBus = EventBusIntImpl.getInstance();
        this.interactor = new AddContactInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.deregister(this);
    }

    @Override
    public void addContact(String email) {
        if (view != null) {
            view.hideInput();
            view.showProgress();
        }
        interactor.execute(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {
        if(view !=null){
            view.hideProgress();
            view.showInput();

            if(event.isError()){
                view.contactNotAdded();
            }
            else{
                view.contactAdded();
            }
        }
    }
}
