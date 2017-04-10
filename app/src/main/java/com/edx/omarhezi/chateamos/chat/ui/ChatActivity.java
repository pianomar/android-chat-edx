package com.edx.omarhezi.chateamos.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.edx.omarhezi.chateamos.R;
import com.edx.omarhezi.chateamos.chat.ChatPresenter;
import com.edx.omarhezi.chateamos.entities.ChatMessage;
import com.edx.omarhezi.chateamos.lib.GlideImageLoader;
import com.edx.omarhezi.chateamos.lib.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements ChatView{

    @BindView(R.id.imgAvatar)
    CircleImageView imgAvatar;
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerViewChats)
    RecyclerView recyclerViewChats;
    @BindView(R.id.editTextMessage)
    EditText editTextMessage;
    @BindView(R.id.btnSendMessage)
    ImageButton btnSendMessage;

    final static public String STATUS_KEY = "online";
    final static public String EMAIL_KEY = "email";
    private ChatPresenter presenter;
    private ChatAdapter adapter;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        imageLoader = new GlideImageLoader(getApplicationContext());

        presenter = new ChatPresenterImpl(this);
        presenter.onCreate();

        setupAdapter();
        setupRecyclerView();
        setupToolbar(getIntent());
    }

    private void setupToolbar(Intent i) {
        String recipient = i.getStringExtra(EMAIL_KEY);
        boolean online = i.getBooleanExtra(STATUS_KEY, false);
        presenter.setChatRecipient(recipient);

        String email = recipient;
        String status = online ? "online" : "offline";
        int color = online ? Color.GREEN : Color.RED;

        txtUser.setText(email);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);

        imageLoader.load(imgAvatar, "https://goo.gl/9REjqV");
    }

    private void setupRecyclerView() {
        recyclerViewChats.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupAdapter() {

    }


    @Override
    public void onMessageSent(String message) {

    }

    @Override
    public void onMessageReceived(ChatMessage message) {
        adapter.add(message);
        recyclerViewChats.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
