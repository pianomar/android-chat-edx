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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edx.omarhezi.chateamos.R;
import com.edx.omarhezi.chateamos.entities.ChatMessage;
import com.edx.omarhezi.chateamos.entities.TextMessage;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;
/**
 * Created by Omar Hezi on 12/04/17.
 */

public class TextMessageDelegates extends AdapterDelegate<List<ChatMessage>> {

    LayoutInflater layoutInflater;
    Context mContext;

    public TextMessageDelegates(Activity activity) {
        layoutInflater = activity.getLayoutInflater();
        mContext = activity;
    }

    @Override
    protected boolean isForViewType(@NonNull List<ChatMessage> items, int position) {
        return items.get(position) instanceof TextMessage;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new TextMessageViewHolder(layoutInflater.inflate(R.layout.content_chat, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<ChatMessage> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        TextMessageViewHolder vh = (TextMessageViewHolder) holder;
        TextMessage message = (TextMessage) items.get(position);

        ChatMessage chatMessage = items.get(position);

        int color = fetchColor((R.attr.colorPrimary));
        int gravity = Gravity.LEFT;

        if(!chatMessage.isSentByMe()){
            color = fetchColor(R.attr.colorAccent);
            gravity = Gravity.RIGHT;
        }

        ((TextMessageViewHolder) holder).message.setBackgroundColor(color);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ((TextMessageViewHolder) holder).message.getLayoutParams();
        params.gravity = gravity;
        ((TextMessageViewHolder) holder).message.setLayoutParams(params);

        vh.message.setText(message.getMesssageContents());
    }

    static class TextMessageViewHolder extends RecyclerView.ViewHolder{
        public TextView message;

        public TextMessageViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.txtViewMessage);
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
