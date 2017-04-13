package com.edx.omarhezi.chateamos.entities;

/**
 * Created by Omar Hezi on 12/04/17.
 */

public class TextMessage extends ChatMessage {
    String messsageContents;

    public TextMessage(String messsageContents) {
        this.messsageContents = messsageContents;
    }

    public String getMesssageContents() {
        return messsageContents;
    }

    public void setMesssageContents(String messsageContents) {
        this.messsageContents = messsageContents;
    }
}
