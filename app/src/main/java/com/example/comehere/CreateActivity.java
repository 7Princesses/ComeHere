package com.example.comehere;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {

    private Button add_pic;
    private TextView test;
    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;
    private String imageFilePath;
    private Uri photoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        add_pic = findViewById(R.id.btn_capture);
        test = findViewById(R.id.test);
        add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog();
            }
        });

        //Spinner
        Spinner unit_spinner = (Spinner)findViewById(R.id.spinner_unit);
        ArrayAdapter unit_Adapter = ArrayAdapter.createFromResource(this,R.array.unit, android.R.layout.simple_spinner_item);
        unit_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_spinner.setAdapter((unit_Adapter));
    }

    //다이얼로그 생성
    private void makeDialog() {
        final CharSequence[] items = {"앨범에서 선택","카메라 촬영"};
        AlertDialog.Builder alt_dia = new AlertDialog.Builder(CreateActivity.this);
        alt_dia.setTitle("사진 업로드");

        //권한 체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.") //권한 팝업창
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check();

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

    //카메라 촬영
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            if(photoFile != null){
                Uri providerURI = FileProvider.getUriForFile(this,getPackageName(),photoFile);
                photoUri = providerURI;
                startActivityForResult(intent,FROM_CAMERA);
            }
        }
    }

    //이미지 생성
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_"+timeStamp +"_";  //파일 이름

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        File imageFile = File.createTempFile(
                imageFileName,
                "jpg",
                storageDir
        );

        imageFilePath = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void selectAlbum() {
        test.setText("앨범에서 선택");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }

        if(requestCode == FROM_ALBUM){
            setImage();
        }
        else if(requestCode==FROM_CAMERA){
            setImage();
        }


    }

    private void setImage() {
        ImageView imageView = (ImageView)findViewById(R.id.iv_result);
        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
        imageView.setImageBitmap(bitmap);
    }

    PermissionListener permissionListener =new PermissionListener() {
        @Override
        //권한 허용했을 때
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(),"권한이 허용됨",Toast.LENGTH_SHORT).show();
        }

        @Override
        //권한을 거부했을 때
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(),"권한이 거부됨",Toast.LENGTH_SHORT).show();

        }
    };
}