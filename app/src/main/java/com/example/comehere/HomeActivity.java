package com.example.comehere;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

public class HomeActivity extends AppCompatActivity {

    private String TAG = "HomeActivity";
    private TextView schoolText;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String currUid;
    private String userNName;
    private String currSchool;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // current User get
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currUid = user.getUid();

        // get Firebase and school change
        database = FirebaseDatabase.getInstance(dburl);
        reference = database.getReference("User").child(currUid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserData curr = dataSnapshot.getValue(UserData.class);
                currSchool = curr.getSchool();
                userNName = curr.getNickname();

                // school text change
                schoolText = (TextView) findViewById(R.id.schoolText);
                schoolText.setText(currSchool);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // search action
        EditText search = (EditText)findViewById(R.id.tx_search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String productName = ((EditText)findViewById(R.id.tx_search)).getText().toString();

                    // post the search data to SearchActivity
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("productName",productName);

                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        // search image action
        ImageView searchImg = (ImageView)findViewById(R.id.searchImg);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = ((EditText)findViewById(R.id.tx_search)).getText().toString();

                // post the search data to SearchActivity
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("productName", productName);
                startActivity(intent);
            }
        });
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(HomeActivity.this, MainActivity_nav.class);
        switch (view.getId()){
            case R.id.btn_cosmetic:
                intent.putExtra("category", "cosmetic");
                break;
            case R.id.btn_bathroom:
                intent.putExtra("category", "bathroom");
                break;
            case R.id.btn_food:
                intent.putExtra("category", "food");
                break;
            case R.id.btn_interior:
                intent.putExtra("category", "interior");
                break;
            case R.id.btn_clean:
                intent.putExtra("category", "clean");
                break;
            case R.id.btn_cloth:
                intent.putExtra("category", "cloth");
                break;
            case R.id.btn_phrase:
                intent.putExtra("category", "phrase");
                break;
            case R.id.btn_kitchen:
                intent.putExtra("category", "kitchen");
                break;
            case R.id.btn_etc:
                intent.putExtra("category", "etc");
                break;
        }
        intent.putExtra("userid", currUid);
        intent.putExtra("username", userNName);
        startActivity(intent);
    }
}