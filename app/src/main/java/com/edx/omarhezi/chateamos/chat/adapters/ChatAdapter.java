package com.edx.omarhezi.chateamos.chat.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edx.omarhezi.chateamos.R;
import com.edx.omarhezi.chateamos.chat.adapters.delegates.ImageAdapterDelegates;
import com.edx.omarhezi.chateamos.chat.adapters.delegates.TextMessageDelegates;
import com.edx.omarhezi.chateamos.entities.ChatMessage;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Omar Hezi on 10/04/17.
 */

public class ChatAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ChatMessage> chatMessages;
    private AdapterDelegatesManager<List<ChatMessage>> delegatesManager;

    public ChatAdapter(Activity activity, Context applicationContext, ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
        this.context = applicationContext;

        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new ImageAdapterDelegates(activity));
        delegatesManager.addDelegate(new TextMessageDelegates(activity));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);

//        int color = fetchColor((R.attr.colorPrimary));
//        int gravity = Gravity.LEFT;
//
//        if(!chatMessage.isSentByMe()){
//            color = fetchColor(R.attr.colorAccent);
//            gravity = Gravity.RIGHT;
//        }
//
//        chatMessage.setBackgroundColor(color);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.itemView.getLayoutParams();
//        params.gravity = gravity;
//        holder.itemView.setLayoutParams(params);

        delegatesManager.onBindViewHolder(chatMessages, position, holder);
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void add(ChatMessage message) {
        if(!chatMessages.contains(message)){
            chatMessages.add(message);
            notifyDataSetChanged();
        }
    }

    private int fetchColor(int color){
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data,
                new int[] {color});
        int returnColor = a.getColor(0,0);
        a.recycle();
        return returnColor;
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(chatMessages, position);
    }
}
