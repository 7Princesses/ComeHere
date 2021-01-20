package com.example.comehere.Chat;

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

import com.example.comehere.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatListFragment extends Fragment {

    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
    private RecyclerView cr_RecyclerView;
    private RecyclerView.Adapter cr_Adapter;
    private RecyclerView.LayoutManager cr_LayoutManager;
    private ArrayList<ChatRoom> cr_List;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;

    // 임시 사용자 이름
    private String username = new ArrayList<>(Arrays.asList("kim", "lee", "park", "choi")).get(3);
    /* //임시 채팅방 입력
        ChatRoom cr = new ChatRoom();
        cr.setCurrentNum(3);
        cr.setMaxNum(10);
        cr.setRoomName("testroom1");
        cr.setChatRoomUsers(Arrays.asList("lee", "choi", "park"));
        dbReference.push().setValue(cr);*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        cr_RecyclerView = getView().findViewById(R.id.rv_chat_list);
        cr_RecyclerView.setHasFixedSize(true);
        cr_LayoutManager = new LinearLayoutManager(getActivity());
        cr_RecyclerView.setLayoutManager(cr_LayoutManager);
        cr_List = new ArrayList<>();

        database = FirebaseDatabase.getInstance(dburl);
        dbReference = database.getReference("ChatRoomInfo");

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cr_List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ChatRoom room = snapshot.getValue(ChatRoom.class);
                    if(room.getChatRoomUsers().contains(username)) cr_List.add(room);
                }
                callBack(cr_List);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ChatListActivity", String.valueOf(databaseError.toException()));
            }
        });
    }

    private void callBack(ArrayList<ChatRoom> cr){
        cr_Adapter = new ChatListAdapter(username, cr, getActivity());
        cr_RecyclerView.setAdapter(cr_Adapter);
        cr_Adapter.notifyDataSetChanged();
    }
}