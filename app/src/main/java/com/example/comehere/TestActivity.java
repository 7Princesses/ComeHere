package com.example.comehere;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String cate = getIntent().getStringExtra("category");

        TextView tv = findViewById(R.id.tv_text);
        tv.setText(cate);
    }
}