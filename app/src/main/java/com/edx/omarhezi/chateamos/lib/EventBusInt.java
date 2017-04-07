package com.edx.omarhezi.chateamos.lib;

/**
 * Created by Lawrence Cermeño on 05/04/17.
 */

public interface EventBusInt {
    public void register(Object subscriber);
    public void deregister(Object subscriber);
    public void post(Object Event);
}
