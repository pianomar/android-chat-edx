package com.edx.omarhezi.chateamos.contacts;

/**
 * Created by Omar Hezi on 07/04/17.
 */

class ContactsListInteractorImpl implements ContactsListInteractor {

    ContactListRepository contactListRepository;

    public ContactsListInteractorImpl() {
        this.contactListRepository = new ContactListRepositoryImpl();
    }

    @Override
    public void subscribeToContactEvents() {
        contactListRepository.subscribeToContactListEvents();
    }

    @Override
    public void unsubscribeFromContactEvents() {
        contactListRepository.unsubscribeFromContactListEvents();
    }

    @Override
    public void destroyListener() {
        contactListRepository.destroyListener();
    }

    @Override
    public void removeContact(String email) {
        contactListRepository.removeContact(email);
    }

    @Override
    public void signOff() {
        contactListRepository.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return contactListRepository.getCurrentUserEmail();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        contactListRepository.changeConnectionStatus(online);
    }
}
