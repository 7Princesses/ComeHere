package com.example.comehere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class HomeActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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