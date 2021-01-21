package com.example.comehere;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ArticleListFragment extends Fragment {

    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
    private RecyclerView atc_RecyclerView;
    private RecyclerView.Adapter atc_Adapter;
    private RecyclerView.LayoutManager atc_LayoutManager;
    private ArrayList<ArticleData> atc_List;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;
    private ArrayList<ChatRoom> allRooms;
    private String category;

    public ArticleListFragment(String category){
        this.category = category;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        atc_RecyclerView = getView().findViewById(R.id.rv_article_list);
        atc_RecyclerView.setHasFixedSize(true);
        atc_LayoutManager = new LinearLayoutManager(getActivity());
        atc_RecyclerView.setLayoutManager(atc_LayoutManager);
        atc_List = new ArrayList<>();
        allRooms = new ArrayList<>();

        database = FirebaseDatabase.getInstance(dburl);
        dbReference = database.getReference("Article");


       /* ArticleData temp = new ArticleData("code", "name", 1000, "url",
                "place", "category", 10, "unit","content", "uid");
        dbReference.push().setValue(temp);*/


        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                atc_List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ArticleData article = snapshot.getValue(ArticleData.class);
                    if(article.getCategory().equals(category))    // category 판별
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
        atc_Adapter = new ArticleListAdapter(atc_List, curNum ,getActivity());
        atc_RecyclerView.setAdapter(atc_Adapter);
        atc_Adapter.notifyDataSetChanged();
    }
}