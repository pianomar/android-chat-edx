package com.edx.omarhezi.chateamos.chat.adapters;

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
import com.edx.omarhezi.chateamos.entities.ChatMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Omar Hezi on 10/04/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    private List<ChatMessage> chatMessages;

    public ChatAdapter(Context applicationContext, ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
        this.context = applicationContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);

        String message = chatMessage.getMsg();
        holder.txtMessage.setText(message);

        int color = fetchColor((R.attr.colorPrimary));
        int gravity = Gravity.LEFT;

        if(!chatMessage.isSentByMe()){
            color = fetchColor(R.attr.colorAccent);
            gravity = Gravity.RIGHT;
        }

        holder.txtMessage.setBackgroundColor(color);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        params.gravity = gravity;
        holder.txtMessage.setLayoutParams(params);
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

    public int fetchColor(int color){
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data,
                new int[] {color});
        int returnColor = a.getColor(0,0);
        a.recycle();
        return returnColor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtViewMessage)
        TextView txtMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
