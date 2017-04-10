package com.edx.omarhezi.chateamos.contacts.addcontact;

/**
 * Created by Lawrence Cermeño on 10/04/17.
 */

class AddContactInteractorImpl implements AddContactInteractor {
    private AddContactRepository repository;

    public AddContactInteractorImpl() {
        this.repository = new AddContactRepositoryImpl();
    }

    @Override
    public void execute(String email) {
        repository.addContact(email);
    }
}
