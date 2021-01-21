package com.example.comehere;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView cRecyclerView;
    private RecyclerView.Adapter cAdapter;
    private RecyclerView.LayoutManager cLayoutManager;
    private List<ChatData> chatDataList;

    private EditText et_chat_box;
    private Button bt_chat_send;

    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
    private FirebaseDatabase database;
    private DatabaseReference dbReference;
    private String username;
    private String master;
    private String cr_name;

    Dialog epicDialog_chat;
    Dialog epicDialog_chat2;
    ImageButton complete_buy_btn;
    ImageButton complete_pay_btn;
    Button btn_complete_pay_yes;
    Button btn_complete_pay_no;
    Button btn_complete_buy_yes;
    Button btn_complete_buy_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance(dburl);
        username = getIntent().getStringExtra("username");
        master = getIntent().getStringExtra("master");
        cr_name = getIntent().getStringExtra("cr_name");
        dbReference = database.getReference("ChatRoom/" + cr_name);


        bt_chat_send = findViewById(R.id.bt_chat_send);
        et_chat_box = findViewById(R.id.et_chat_box);

        epicDialog_chat = new Dialog(this);
        epicDialog_chat2 = new Dialog(this);
        complete_buy_btn = (ImageButton) findViewById(R.id.complete_buy_btn);
        complete_pay_btn = (ImageButton) findViewById(R.id.complete_pay_btn);


        complete_pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPayCompletePopup();
            }
        });
        complete_buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowBuyCompletePopup();
            }
        });


        bt_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et_chat_box.getText().toString();
                if(msg != null) {
                    ChatData chat = new ChatData();
                    chat.setName(username);
                    chat.setMsg(msg);
                    et_chat_box.setText(null);

                    dbReference.push().setValue(chat);
                }
            }
        });

        cRecyclerView = findViewById(R.id.rv_chat_do);
        cRecyclerView.setHasFixedSize(true);
        cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        chatDataList = new ArrayList<>();
        cAdapter = new ChatAdapter(chatDataList, ChatActivity.this, username, master);
        cRecyclerView.setAdapter(cAdapter);

        ((TextView)findViewById(R.id.room_name)).setText(cr_name);


        dbReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatData chat = dataSnapshot.getValue(ChatData.class);
                ((ChatAdapter)cAdapter).addChat(chat);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    public void ShowBuyCompletePopup(){
        epicDialog_chat.setContentView(R.layout.buy_complete_popup);
        btn_complete_buy_yes = (Button) epicDialog_chat.findViewById(R.id.btn_complete_buy_yes);
        btn_complete_buy_no = (Button) epicDialog_chat.findViewById(R.id.btn_complete_buy_no);

        btn_complete_buy_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog_chat.dismiss();
                //db 수정 ?
            }
        });
        btn_complete_buy_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog_chat.dismiss();
                //db 수정 ?
            }
        });
        epicDialog_chat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog_chat.show();
    }
    public void ShowPayCompletePopup(){
        epicDialog_chat2.setContentView(R.layout.pay_complete_popup);
        btn_complete_pay_yes = (Button) epicDialog_chat2.findViewById(R.id.btn_complete_pay_yes);
        btn_complete_pay_no = (Button) epicDialog_chat2.findViewById(R.id.btn_complete_pay_no);
        btn_complete_pay_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog_chat2.dismiss();
                //db 수정 ?
            }
        });
        btn_complete_pay_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog_chat2.dismiss();
                //db 수정 ?
            }
        });

        epicDialog_chat2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog_chat2.show();
    }
}