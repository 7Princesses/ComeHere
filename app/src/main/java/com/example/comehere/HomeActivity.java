package com.example.comehere;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
                User curr = dataSnapshot.getValue(User.class);
                currSchool = curr.getSchool();
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
                intent.putExtra("productName",productName);

                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
    }


    public void onButtonClick(View view) {
        //넘기는거 이름을 이채연거랑 머지한 후 ArticleListActivity 로 바꾸고 testactivity는 지워도됨
        Intent intent = new Intent(HomeActivity.this,TestActivity.class);
        switch (view.getId()){
            case R.id.btn_cosmetic:
                intent.putExtra("category", "cosmetic");
            case R.id.btn_bathroom:
                intent.putExtra("category", "bathroom");
            case R.id.btn_food:
                intent.putExtra("category", "food");
            case R.id.btn_interior:
                intent.putExtra("category", "interior");
            case R.id.btn_clean:
                intent.putExtra("category", "clean");
            case R.id.btn_cloth:
                intent.putExtra("category", "cloth");
            case R.id.btn_phrase:
                intent.putExtra("category", "phrase");
            case R.id.btn_kitchen:
                intent.putExtra("category", "kitchen");
            case R.id.btn_etc:
                intent.putExtra("category", "etc");
            default:
                //
        }
        startActivity(intent);
    }
}