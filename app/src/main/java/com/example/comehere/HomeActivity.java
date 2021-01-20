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
        switch (view.getId()){
            case R.id.btn_cosmetic:
                //화장품에 대한 정보를 넘겨줄 준비
            case R.id.btn_bathroom:
                //
            case R.id.btn_food:
                //
            case R.id.btn_interior:
                //
            case R.id.btn_clean:
                //
            case R.id.btn_cloth:
                //
            case R.id.btn_phrase:
                //
            case R.id.btn_kitchen:
                //
            case R.id.btn_etc:
                //
            default:
                Intent intent = new Intent(HomeActivity.this,TestActivity.class);
                startActivity(intent);
        }
    }
}