package com.example.comehere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ArrayList<ListData> arrayList;
    private ListAdapter mainAdapter;
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

        mainAdapter = new ListAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        ListData listData1 = new ListData("@mipmap/ic_launcher", "휴지 나눔",500,"개",5,7);
        arrayList.add(listData1);
        mainAdapter.notifyDataSetChanged();
        ListData listData2 = new ListData("@mipmap/ic_launcher", "바디워시 나눔",500,"개",5,7);
        arrayList.add(listData2);
        mainAdapter.notifyDataSetChanged();
        ListData listData3 = new ListData("@mipmap/ic_launcher", "마하그리드 옷",500,"개",5,7);
        arrayList.add(listData3);
        mainAdapter.notifyDataSetChanged();
        ListData listData4 = new ListData("@mipmap/ic_launcher", "휴지 나눔",500,"개",5,7);
        arrayList.add(listData4);
        mainAdapter.notifyDataSetChanged();
        ListData listData5 = new ListData("@mipmap/ic_launcher", "휴지 나눔",500,"개",5,7);
        arrayList.add(listData5);
        mainAdapter.notifyDataSetChanged();
        ListData listData6 = new ListData("@mipmap/ic_launcher", "휴지 나눔",500,"개",5,7);
        arrayList.add(listData6);
        mainAdapter.notifyDataSetChanged();
        ListData listData7 = new ListData("@mipmap/ic_launcher", "휴지 나눔",500,"개",5,7);
        arrayList.add(listData7);
        mainAdapter.notifyDataSetChanged();


    }
}
