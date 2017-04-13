package com.edx.omarhezi.chateamos.chat.adapters.delegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.edx.omarhezi.chateamos.R;
import com.edx.omarhezi.chateamos.entities.ChatMessage;
import com.edx.omarhezi.chateamos.entities.ImageMessage;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

/**
 * Created by Omar Hezi on 12/04/17.
 */

public class ImageAdapterDelegates extends AdapterDelegate<List<ChatMessage>>{

    private LayoutInflater layoutInflater;
    private RequestManager requestManager;

    public ImageAdapterDelegates(Activity activity) {
        this.requestManager = Glide.with(activity);
        layoutInflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<ChatMessage> items, int position) {
        return items.get(position) instanceof ImageMessage;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ImageMessageViewHolder(layoutInflater.inflate(R.layout.image_message, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<ChatMessage> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ImageMessageViewHolder vh = (ImageMessageViewHolder) holder;
        ImageMessage imageMessage = (ImageMessage) items.get(position);
        requestManager.load(imageMessage.getImage()).into(vh.message);
    }


    static class ImageMessageViewHolder extends RecyclerView.ViewHolder{
        public ImageView message;

        public ImageMessageViewHolder(View itemView) {
            super(itemView);
            message = (ImageView) itemView.findViewById(R.id.imageMessageImgView);
        }
    }
}
