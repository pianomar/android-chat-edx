package com.edx.omarhezi.chateamos.chat.adapters.delegates;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

    Context mContext;


    public ImageAdapterDelegates(Activity activity) {
        this.requestManager = Glide.with(activity);
        layoutInflater = activity.getLayoutInflater();
        mContext = activity;
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

        ChatMessage chatMessage = items.get(position);

        int color = fetchColor((R.attr.colorPrimary));
        int gravity = Gravity.LEFT;

        if(!chatMessage.isSentByMe()){
            color = fetchColor(R.attr.colorAccent);
            gravity = Gravity.RIGHT;
        }

        ((ImageMessageViewHolder) holder).message.setBackgroundColor(color);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ((ImageMessageViewHolder) holder).message.getLayoutParams();
        params.gravity = gravity;
        ((ImageMessageViewHolder) holder).message.setLayoutParams(params);

        requestManager.load(imageMessage.getImage()).into(vh.message);
    }


    static class ImageMessageViewHolder extends RecyclerView.ViewHolder{
        public ImageView message;

        public ImageMessageViewHolder(View itemView) {
            super(itemView);
            message = (ImageView) itemView.findViewById(R.id.imageMessageImgView);
        }
    }

    public int fetchColor(int color){
        TypedValue typedValue = new TypedValue();
        TypedArray a = mContext.obtainStyledAttributes(typedValue.data,
                new int[] {color});
        int returnColor = a.getColor(0,0);
        a.recycle();
        return returnColor;
    }
}
