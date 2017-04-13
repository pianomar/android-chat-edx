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

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(chatMessages, position);
    }
}
