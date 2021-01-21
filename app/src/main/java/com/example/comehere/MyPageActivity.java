package com.example.comehere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPageActivity extends AppCompatActivity {

    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
    private UserData mp_data;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;

    private ArrayList<ArticleData> atc_List;
    private RecyclerView atc_RecyclerView;
    private RecyclerView.Adapter atc_Adapter;
    private RecyclerView.LayoutManager atc_LayoutManager;

    private String tempName = "tetete"; // db에 있는 임시 닉네임
    private String tempUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mypage);

        // 사용자 정보 가져오기
        database = FirebaseDatabase.getInstance(dburl);
        dbReference = database.getReference("User");

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserData userData = snapshot.getValue(UserData.class);
                    if(userData.getNickname().equals(tempName))
                        mp_data = userData;
                }
                profileWrite();
                postWrite();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ArticleDataActivity", String.valueOf(databaseError.toException()));
            }
        });
    }

    private void profileWrite(){
        TextView tv_name = findViewById(R.id.userNickname);
        TextView tv_email = findViewById(R.id.userId);
        TextView tv_school = findViewById(R.id.school);
        TextView tv_studentId = findViewById(R.id.identity);

        tv_name.setText(mp_data.getNickname());
        tv_email.setText(mp_data.getNickname()); // 수정 필요
        tv_school.setText(mp_data.getSchool());
        tv_studentId.setText(String.valueOf(mp_data.getStudentId()));
    }

    private void postWrite(){
        // 쓴 글 가져오기
        atc_RecyclerView = findViewById(R.id.mypage_rv);
        atc_RecyclerView.setHasFixedSize(true);
        atc_LayoutManager = new LinearLayoutManager(this);
        atc_RecyclerView.setLayoutManager(atc_LayoutManager);
        atc_List = new ArrayList<>();
        tempUid = mp_data.getUID(); //임시 uid

        database = FirebaseDatabase.getInstance(dburl);
        dbReference = database.getReference("Article");

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                atc_List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ArticleData article = snapshot.getValue(ArticleData.class);
                    if(article.getUID().equals(tempUid))   // uid 판별
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