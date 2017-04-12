package com.edx.omarhezi.chateamos.lib;

/**
 * Created by Omar Hezi on 05/04/17.
 */

public interface EventBusInt {
     void register(Object subscriber);
     void deregister(Object subscriber);
     void post(Object Event);
}
