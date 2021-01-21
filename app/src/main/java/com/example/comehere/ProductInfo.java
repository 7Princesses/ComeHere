package com.example.comehere;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductInfo extends AppCompatActivity {

    Dialog buyCheckDialog;
    Button buyYesBtn, buyNoBtn, buyBtn;

    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
    private ArticleData atc_data;
    private String nickname;
    private ChatRoom chatRoom = null;
    private String chatRoomKey;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        final String prodName = getIntent().getStringExtra("productName");

        // 사용자 정보 가져오기
        database = FirebaseDatabase.getInstance(dburl);
        dbReference = database.getReference("Article");

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ArticleData articleData = snapshot.getValue(ArticleData.class);
                    if (articleData.getProductName().equals(prodName))
                        atc_data = articleData;
                }
                getNickName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ArticleDataActivity", String.valueOf(databaseError.toException()));
            }
        });
    }


    private void getNickName() {
        dbReference = database.getReference("User");

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData userData = snapshot.getValue(UserData.class);
                    if (userData.getUID().equals(atc_data.getUID()))
                        nickname = userData.getNickname();
                }
                getChatRoom();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ArticleDataActivity", String.valueOf(databaseError.toException()));
            }
        });
    }

    private void getChatRoom() {
        dbReference = database.getReference("ChatRoomInfo");

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatRoom cr = snapshot.getValue(ChatRoom.class);
                    if (cr.getRoomName().equals(atc_data.getProductName())) {
                        chatRoom = cr;
                        chatRoomKey = snapshot.getKey();
                    }
                }
                articleLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ArticleDataActivity", String.valueOf(databaseError.toException()));
            }
        });
    }

    private void articleLoad() {
        TextView tv_product_name = findViewById(R.id.tv_product_name);
        TextView tv_product_type = findViewById(R.id.tv_product_type);
        TextView tv_profile = findViewById(R.id.tv_profile);
        TextView tv_product_price = findViewById(R.id.tv_product_price);
        TextView textView4 = findViewById(R.id.textView4);
        TextView tv_product_link = findViewById(R.id.tv_product_link);
        TextView tv_product_place = findViewById(R.id.tv_product_place);
        TextView tv_content = findViewById(R.id.tv_content);
        Spinner spinner_quan = findViewById(R.id.spinner_quan);

        tv_product_name.setText(atc_data.getProductName());
        tv_product_type.setText(atc_data.getCategory());
        tv_profile.setText(nickname);
        tv_product_price.setText(String.valueOf(atc_data.getTotalPrice()));
        textView4.setText((chatRoom.getCurrentNum() + " / " + atc_data.getProductCount()));
        tv_product_link.setText(atc_data.getURL());
        tv_product_place.setText(atc_data.getTradePlace());
        tv_content.setText(atc_data.getContent());

        List<String> list = new ArrayList<>();
        for (int i = 1; i <= atc_data.getProductCount() - chatRoom.getCurrentNum(); i++)
            list.add(String.valueOf(i));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.quan_spinner_style, list);
        spinner_quan.setAdapter(arrayAdapter);

        tv_product_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(atc_data.getURL());
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        //팝업창
        buyCheckDialog = new Dialog(this);
        buyBtn = (Button) findViewById(R.id.btn_buy);

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowBuyCheckPopup();
            }
        });
    }


    //구매 신청 팝업창 띄우는 함수
    public void ShowBuyCheckPopup() {

        buyCheckDialog.setContentView(R.layout.buy_check_popup);
        buyYesBtn = (Button) buyCheckDialog.findViewById(R.id.btn_buy_yes);
        buyNoBtn = (Button) buyCheckDialog.findViewById(R.id.btn_buy_no);

        //구매 신청 -> 아니오
        buyNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyCheckDialog.dismiss();
            }
        });

        //구매 신청 -> 예
        buyYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미 신청 한 항목 예외처리 해야함
                Toast.makeText(getApplicationContext(), "구매 신청이 완료되었습니다! 채팅 목록을 확인해보세요!", Toast.LENGTH_LONG).show();

                String s = ((Spinner)findViewById(R.id.spinner_quan)).getSelectedItem().toString();
                Integer curNum = Integer.parseInt(s);

                dbReference = database.getReference("ChatRoomInfo");


                ChatRoom new_cr = new ChatRoom();
                List<String> newusers = chatRoom.getChatRoomUsers();
                newusers.add(nickname);

                new_cr.setChatRoomUsers(newusers);
                new_cr.setRoomName(chatRoom.getRoomName());
                new_cr.setMaxNum(chatRoom.getMaxNum());
                new_cr.setCurrentNum(chatRoom.getCurrentNum() + curNum);
                new_cr.setMaster(chatRoom.getMaster());

                dbReference.child(chatRoomKey).removeValue();
                dbReference.push().setValue(new_cr);

                buyCheckDialog.dismiss();
            }
        });


        buyCheckDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        buyCheckDialog.show();

    }
}

