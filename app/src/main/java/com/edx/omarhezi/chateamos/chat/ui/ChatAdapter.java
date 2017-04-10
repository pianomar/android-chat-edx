package com.edx.omarhezi.chateamos.chat.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.edx.omarhezi.chateamos.entities.ChatMessage;

/**
 * Created by Lawrence Cermeño on 10/04/17.
 */

class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void add(ChatMessage message) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
