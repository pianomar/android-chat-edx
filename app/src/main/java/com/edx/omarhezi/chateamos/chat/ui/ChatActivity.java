package com.edx.omarhezi.chateamos.chat.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.edx.omarhezi.chateamos.R;
import com.edx.omarhezi.chateamos.chat.ChatPresenterImpl;
import com.edx.omarhezi.chateamos.chat.adapters.ChatAdapter;
import com.edx.omarhezi.chateamos.entities.ChatMessage;
import com.edx.omarhezi.chateamos.lib.GlideImageLoader;
import com.edx.omarhezi.chateamos.lib.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements ChatView {

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
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;

    final static public String STATUS_KEY = "online";
    final static public String EMAIL_KEY = "email";

    private ChatPresenterImpl presenter;
    private ChatAdapter adapter;
    private ImageLoader imageLoader;
    private Uri uriImage;

    final static public int TAKE_PHOTO = 1;
    final static public int CHOOSE_FROM_GALLERY = 2;

    private Bitmap messageBitmap;
    private int permitted = 0;

    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        imageLoader = new GlideImageLoader(getApplicationContext());

        presenter = new ChatPresenterImpl(this);
        presenter.onCreate();

        messageBitmap = null;

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

        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        recyclerViewChats.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupAdapter() {
        adapter = new ChatAdapter(this, getApplicationContext(), new ArrayList<ChatMessage>());
        recyclerViewChats.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gallery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_FROM_GALLERY);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMessageReceived(ChatMessage message) {
        adapter.add(message);
        recyclerViewChats.scrollToPosition(adapter.getItemCount() - 1);
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

    @OnClick(R.id.btnSendMessage)
    public void sendMessage() {
        String message = editTextMessage.getText().toString();
        String type = messageBitmap == null ? "text" : "image";

        if (!message.equals("")) {
            presenter.sendMessage(message, type);
            editTextMessage.setText("");
        }
    }

    @OnClick(R.id.btnCamera)
    public void sendImageMessage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    private void capturarFoto() {
        String file = dir+ DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString()+".jpg";


        File newfile = new File(file);
        final String dir =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/Folder/";
        File newdir = new File(dir);
        newdir.mkdirs();
        try {
            newfile.createNewFile();
        } catch (IOException e) {}

        Uri outputFileUri = Uri.fromFile(newfile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");

//            Uri uriImage = data.getData();


            uriImage = Uri.fromFile(newdir);

            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uriImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            presenter.uploadImage(inputStream);
        }
    }
}
