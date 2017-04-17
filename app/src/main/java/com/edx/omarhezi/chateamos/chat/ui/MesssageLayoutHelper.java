package com.edx.omarhezi.chateamos.chat.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.edx.omarhezi.chateamos.R;
import com.edx.omarhezi.chateamos.chat.adapters.delegates.ImageAdapterDelegates;
import com.edx.omarhezi.chateamos.entities.ChatMessage;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

/**
 * Created by Omar Hezi on 17/04/17.
 */

public class MesssageLayoutHelper {
    public void setMessageLayout(ChatMessage chatMessage, View message, Context context){
        int color = R.color.receivedColor;
        int gravity = Gravity.LEFT;

        if(!chatMessage.isSentByMe()){
            color = R.color.sentColor;
            gravity = Gravity.RIGHT;
        }

        message.setBackgroundColor(ContextCompat.getColor(context,color));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) message.getLayoutParams();
        params.gravity = gravity;
        message.setLayoutParams(params);
    }
}
