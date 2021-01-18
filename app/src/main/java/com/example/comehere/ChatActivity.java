package com.example.comehere;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView cRecyclerView;
    private RecyclerView.Adapter cAdapter;
    private RecyclerView.LayoutManager cLayoutManager;
    private List<ChatData> chatDataList;

    private EditText et_chat_box;
    private Button bt_chat_send;

    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
    private FirebaseDatabase database = FirebaseDatabase.getInstance(dburl);
    private DatabaseReference myRef = database.getReference("message/user");


    // 임시 이름
    private String name1 = "user1";
    private String name2 = "user3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bt_chat_send = findViewById(R.id.bt_chat_send);
        et_chat_box = findViewById(R.id.et_chat_box);

        bt_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et_chat_box.getText().toString();
                if(msg != null) {
                    ChatData chat = new ChatData();
                    chat.setName(name2);    //sender name
                    chat.setMsg(msg);
                    et_chat_box.setText(null);

                    myRef.push().setValue(chat);
                }
            }
        });

        cRecyclerView = findViewById(R.id.rv_chat_do);
        cRecyclerView.setHasFixedSize(true);
        cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        chatDataList = new ArrayList<>();
        cAdapter = new ChatAdapter(chatDataList, ChatActivity.this, name2);     // app user name
        cRecyclerView.setAdapter(cAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
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
}