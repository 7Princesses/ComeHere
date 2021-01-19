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

public class MainActivity extends AppCompatActivity {

    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        MainData mainData1 = new MainData("@mipmap/ic_launcher", "휴지 나눔",500,"개",5,7);
        arrayList.add(mainData1);
        mainAdapter.notifyDataSetChanged();
        MainData mainData2 = new MainData("@mipmap/ic_launcher", "바디워시 나눔",500,"개",5,7);
        arrayList.add(mainData2);
        mainAdapter.notifyDataSetChanged();
        MainData mainData3 = new MainData("@mipmap/ic_launcher", "마하그리드 옷",500,"개",5,7);
        arrayList.add(mainData3);
        mainAdapter.notifyDataSetChanged();
        MainData mainData4 = new MainData("@mipmap/ic_launcher", "휴지 나눔",500,"개",5,7);
        arrayList.add(mainData4);
        mainAdapter.notifyDataSetChanged();
        MainData mainData5 = new MainData("@mipmap/ic_launcher", "휴지 나눔",500,"개",5,7);
        arrayList.add(mainData5);
        mainAdapter.notifyDataSetChanged();
        MainData mainData6 = new MainData("@mipmap/ic_launcher", "휴지 나눔",500,"개",5,7);
        arrayList.add(mainData6);
        mainAdapter.notifyDataSetChanged();
        MainData mainData7 = new MainData("@mipmap/ic_launcher", "휴지 나눔",500,"개",5,7);
        arrayList.add(mainData7);
        mainAdapter.notifyDataSetChanged();


    }
}
