package com.edx.omarhezi.chateamos.lib;

/**
 * Created by Omar Hezi on 05/04/17.
 */

public class EventBusIntImpl implements EventBusInt {
    org.greenrobot.eventbus.EventBus eventBus;

    public static class SingletonHolder {
        private static final EventBusIntImpl INSTANCE = new EventBusIntImpl();
    }


    public static EventBusIntImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public EventBusIntImpl() {
        this.eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void deregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object Event) {
        eventBus.post(Event);
    }


}
