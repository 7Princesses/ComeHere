package com.example.comehere;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ClipData;
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
    private Uri cam_photoUri,albumURI,al_photoUri;
    ArrayList imageList = new ArrayList<>();

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

        alt_dia.setItems(items,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //권한 체크
                        TedPermission.with(getApplicationContext())
                                .setPermissionListener(permissionListener)
                                .setRationaleMessage("카메라 권한이 필요합니다.") //권한 팝업창
                                .setDeniedMessage("거부하셨습니다.")
                                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                                .check();
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
                cam_photoUri = providerURI;
                intent.putExtra(MediaStore.EXTRA_OUTPUT,cam_photoUri);
                startActivityForResult(intent,FROM_CAMERA);
            }
        }
    }

    //앨범열기
    private void selectAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,FROM_ALBUM);
    }

    //이미지 생성
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_"+timeStamp +".jpg";  //파일 이름

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }

        if(requestCode == FROM_ALBUM && resultCode==RESULT_OK){
            File albumFile = null;
            try{
                albumFile = createImageFile();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            if(albumFile != null){
                albumURI = Uri.fromFile(albumFile);
            }
            ClipData clipData = data.getClipData();

            if(clipData.getItemCount() >10){
                Toast.makeText(CreateActivity.this,"사진은 10개까지 선택가능 합니다.",Toast.LENGTH_SHORT).show();
                return;
            }
            else if(clipData.getItemCount()>1 && clipData.getItemCount()<10){
                for(int i=0; i<clipData.getItemCount();i++){
                    imageList.add(String.valueOf(clipData.getItemAt(i).getUri()));
                }
            }
            if(clipData!=null){
                for(int i=0; i<5;i++){
                    if(i<clipData.getItemCount()){
                        Uri urione = clipData.getItemAt(i).getUri();
                        if(i==0){
                            ImageView imageView = (ImageView)findViewById(R.id.iv_result);
                            imageView.setImageURI(urione);
                        }
                        else if(i==1){
                            ImageView imageView = (ImageView)findViewById(R.id.iv_result2);
                            imageView.setImageURI(urione);
                        }
                        else if(i==2){
                            ImageView imageView = (ImageView)findViewById(R.id.iv_result3);
                            imageView.setImageURI(urione);
                        }
                        else if(i==3){
                            ImageView imageView = (ImageView)findViewById(R.id.iv_result4);
                            imageView.setImageURI(urione);
                        }
                        else if(i==4){
                            ImageView imageView = (ImageView)findViewById(R.id.iv_result5);
                            imageView.setImageURI(urione);
                        }
                        else if(i==5){
                            ImageView imageView = (ImageView)findViewById(R.id.iv_result6);
                            imageView.setImageURI(urione);
                        }
                    }
                }
            }
            else if(al_photoUri!=null){
                test.setText("취소");
            }

        }
        else if(requestCode==FROM_CAMERA && resultCode==RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView)findViewById(R.id.iv_result);
            if(photo != null){
                imageView.setImageBitmap(photo);
                test.setText("사진");
            }
            else{
                test.setText("없음");
            }
        }
    }

    private void setImage() {
        ImageView imageView = (ImageView)findViewById(R.id.iv_result);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath,options);
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