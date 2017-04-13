package com.edx.omarhezi.chateamos.entities;

/**
 * Created by Omar Hezi on 12/04/17.
 */

public class ImageMessage extends ChatMessage {
    private String image;

    public ImageMessage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
