package com.example.comehere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.comhere.HelloKt;

import java.util.ArrayList;

public class MyPageActivity extends AppCompatActivity {

    private ArrayList<MyPageData> arrayList;
    private MyPageAdapter myPageAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        recyclerView = (RecyclerView)findViewById(R.id.mypage_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        myPageAdapter = new MyPageAdapter(arrayList);
        recyclerView.setAdapter(myPageAdapter);

        MyPageData myPageData1 = new MyPageData("chae0382", "chae0382@gmail.com","인하대학교",12191656);
        arrayList.add(myPageData1);
        myPageAdapter.notifyDataSetChanged();
    }
}
