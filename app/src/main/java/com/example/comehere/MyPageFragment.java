package com.example.comehere;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPageFragment extends Fragment {

    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
    private UserData mp_data;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;
    private ArrayList<ChatRoom> allRooms;

    private ArrayList<ArticleData> atc_List;
    private RecyclerView atc_RecyclerView;
    private RecyclerView.Adapter atc_Adapter;
    private RecyclerView.LayoutManager atc_LayoutManager;
    private String uid;

    public MyPageFragment(String uid){
        this.uid = uid;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // 사용자 정보 가져오기
        database = FirebaseDatabase.getInstance(dburl);
        dbReference = database.getReference("User");
        allRooms = new ArrayList<>();

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserData userData = snapshot.getValue(UserData.class);
                    if(userData.getUID().equals(uid))
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
        TextView tv_name = getView().findViewById(R.id.userNickname);
        TextView tv_email = getView().findViewById(R.id.userId);
        TextView tv_school = getView().findViewById(R.id.school);
        TextView tv_studentId = getView().findViewById(R.id.identity);

        tv_name.setText(mp_data.getNickname());
        tv_email.setText(mp_data.getNickname()); // 수정 필요
        tv_school.setText(mp_data.getSchool());
        tv_studentId.setText(String.valueOf(mp_data.getStudentId()));
    }

    private void postWrite(){
        // 쓴 글 가져오기
        atc_RecyclerView = getView().findViewById(R.id.mypage_rv);
        atc_RecyclerView.setHasFixedSize(true);
        atc_LayoutManager = new LinearLayoutManager(getActivity());
        atc_RecyclerView.setLayoutManager(atc_LayoutManager);
        atc_List = new ArrayList<>();

        database = FirebaseDatabase.getInstance(dburl);
        dbReference = database.getReference("Article");

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                atc_List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ArticleData article = snapshot.getValue(ArticleData.class);
                    if(article.getUID().equals(uid))   // uid 판별
                        atc_List.add(article);
                }
                findCurNum();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ArticleDataActivity", String.valueOf(databaseError.toException()));
            }
        });
    }

    private void findCurNum(){
        dbReference = database.getReference("ChatRoomInfo");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatRoom cr = snapshot.getValue(ChatRoom.class);
                    allRooms.add(cr);
                }
                callBack();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ArticleDataActivity", String.valueOf(databaseError.toException()));
            }
        });
    }

    private void callBack(){
        ArrayList<Integer> curNum = new ArrayList<>();

        for(ArticleData atc : atc_List){
            for(ChatRoom c : allRooms){
                if(atc.getProductName().equals(c.getRoomName())){
                    curNum.add(c.getCurrentNum());
                }
            }
        }
        atc_Adapter = new ArticleListAdapter(atc_List, curNum, getActivity());
        atc_RecyclerView.setAdapter(atc_Adapter);
        atc_Adapter.notifyDataSetChanged();
    }

}