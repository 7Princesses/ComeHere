package com.example.comehere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArticleListActivity extends AppCompatActivity {

    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
    private RecyclerView atc_RecyclerView;
    private RecyclerView.Adapter atc_Adapter;
    private RecyclerView.LayoutManager atc_LayoutManager;
    private ArrayList<ArticleData> atc_List;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;

    private String category = "food";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_article_list);

        atc_RecyclerView = findViewById(R.id.rv_article_list);
        atc_RecyclerView.setHasFixedSize(true);
        atc_LayoutManager = new LinearLayoutManager(this);
        atc_RecyclerView.setLayoutManager(atc_LayoutManager);
        atc_List = new ArrayList<>();

        database = FirebaseDatabase.getInstance(dburl);
        dbReference = database.getReference("ArticleDataInfo");


       /* ArticleData temp = new ArticleData("code", "name", 1000, "url",
                "place", "category", 10, "unit","content", "uid");
        dbReference.push().setValue(temp);*/



        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                atc_List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ArticleData article = snapshot.getValue(ArticleData.class);
                    /*if(article.getCategory().equals(category)) */     // category 판별
                    atc_List.add(article);
                }
                callBack(atc_List);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ArticleDataActivity", String.valueOf(databaseError.toException()));
            }
        });
    }

    private void callBack(ArrayList<ArticleData> atc){
        atc_Adapter = new ArticleListAdapter(atc, this);
        atc_RecyclerView.setAdapter(atc_Adapter);
        atc_Adapter.notifyDataSetChanged();
    }
}