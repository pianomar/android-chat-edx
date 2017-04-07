package com.edx.omarhezi.chateamos.contacts.ui.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edx.omarhezi.chateamos.R;
import com.edx.omarhezi.chateamos.entities.User;
import com.edx.omarhezi.chateamos.lib.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lawrence Cermeño on 06/04/17.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {


    private List<User> contactList;
    private ImageLoader imageLoader;
    private OnItemClickListenerInt onItemClickListenerInt;

    public ContactListAdapter(List<User> contactList, ImageLoader imageLoader, OnItemClickListenerInt onItemClickListenerInt) {
        this.contactList = contactList;
        this.imageLoader = imageLoader;
        this.onItemClickListenerInt = onItemClickListenerInt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = contactList.get(position);
        holder.setClickListener(user, onItemClickListenerInt);

        String email = user.getEmail();
        boolean online = user.isOnline();
        String status = online ? "online" : "offline";
        int color = online ? Color.GREEN : Color.RED;

        holder.txtUser.setText(email);
        holder.txtStatus.setText(status);
        holder.txtStatus.setTextColor(color);

        imageLoader.load(holder.imgAvatar, "https://goo.gl/9REjqV");
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void add(User user) {
        if(!contactList.contains(user)){
            contactList.add(user);
            notifyDataSetChanged();
        }
    }

    public void update(User user) {
        int position = contactList.indexOf(user);
        if(contactList.contains(user)){
            contactList.set(position, user);
            notifyDataSetChanged();
        }
    }

    public void remove(User user) {
        if(contactList.contains(user)){
            contactList.remove(user);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @BindView(R.id.txtUser)
        TextView txtUser;
        @BindView(R.id.txtStatus)
        TextView txtStatus;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        private void setClickListener(final User user, final OnItemClickListenerInt listener){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(user);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(user);
                    return false;
                }
            });
        }
    }
}
