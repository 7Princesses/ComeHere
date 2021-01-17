package com.example.comehere;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateActivity extends AppCompatActivity {
    Button add_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        add_pic = findViewById(R.id.btn_capture);
        add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog();
            }
        });
    }

    private void makeDialog() {
        final CharSequence[] items = {"앨범에서 선택","카메라 촬영"};
        AlertDialog.Builder alt_dia = new AlertDialog.Builder(CreateActivity.this);
        alt_dia.setTitle("사진 업로드");
        alt_dia.setItems(items,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(id==0)
                            selectAlbum();
                        else
                            takePhoto();
                    }
                });
        AlertDialog alert = alt_dia.create();
        alert.show();
    }

    private void takePhoto() {
    }

    private void selectAlbum() {
    }
}