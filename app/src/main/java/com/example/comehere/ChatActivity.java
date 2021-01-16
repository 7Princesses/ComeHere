package com.example.comehere;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        // Write a message to the database

        String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
        FirebaseDatabase database = FirebaseDatabase.getInstance(dburl);
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("hello world!");
    }
}