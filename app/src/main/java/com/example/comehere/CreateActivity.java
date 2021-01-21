package com.example.comehere;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {

    private ImageButton add_pic;
    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;
    private String imageFilePath;
    private Uri cam_photoUri,al_photoUri;
    ArrayList<Bitmap> imageList = new ArrayList<Bitmap>();  //이미지가 bitmap으로 저장되어 있는 파일

    ImageView iv_result1,iv_result2,iv_result3,iv_result4,iv_result5,iv_result6,iv_result7,iv_result8,iv_result9,iv_result10;

    private int count=0;
    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Auth Initialize
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // image
        add_pic = findViewById(R.id.btn_capture);

        iv_result1 = findViewById(R.id.iv_result1); iv_result1.setVisibility(View.GONE);
        iv_result2 = findViewById(R.id.iv_result2); iv_result2.setVisibility(View.GONE);
        iv_result3 = findViewById(R.id.iv_result3); iv_result3.setVisibility(View.GONE);
        iv_result4 = findViewById(R.id.iv_result4); iv_result4.setVisibility(View.GONE);
        iv_result5 = findViewById(R.id.iv_result5); iv_result5.setVisibility(View.GONE);
        iv_result6 = findViewById(R.id.iv_result6); iv_result6.setVisibility(View.GONE);
        iv_result7 = findViewById(R.id.iv_result7); iv_result7.setVisibility(View.GONE);
        iv_result8 = findViewById(R.id.iv_result8); iv_result8.setVisibility(View.GONE);
        iv_result9 = findViewById(R.id.iv_result9); iv_result9.setVisibility(View.GONE);
        iv_result10 = findViewById(R.id.iv_result10); iv_result10.setVisibility(View.GONE);

        add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog();
            }
        });

        // Spinner unit
        Spinner unit_spinner = (Spinner)findViewById(R.id.spinner_unit);
        ArrayAdapter unit_adapter = new ArrayAdapter<String>(this, R.layout.select_spinner, getResources().getStringArray(R.array.unit));
        unit_adapter.setDropDownViewResource(R.layout.custom_spinner);
        unit_spinner.setAdapter(unit_adapter);

        // Spinner category
        Spinner category_spinner = (Spinner)findViewById(R.id.categorySpinner);
        ArrayAdapter category_adapter = new ArrayAdapter<String>(this, R.layout.select_spinner, getResources().getStringArray(R.array.category));
        category_adapter.setDropDownViewResource(R.layout.custom_spinner);
        category_spinner.setAdapter(category_adapter);

        // Spinner count
        Spinner count_spinner = (Spinner)findViewById(R.id.count_spinner);
        ArrayAdapter count_adapter = new ArrayAdapter<String>(this, R.layout.select_spinner, getResources().getStringArray(R.array.counting));
        count_adapter.setDropDownViewResource(R.layout.custom_spinner);
        count_spinner.setAdapter(count_adapter);

        // Spinner position
        Spinner position_spinner = (Spinner)findViewById(R.id.positionSpinner);
        ArrayAdapter position_adapter = new ArrayAdapter<String>(this, R.layout.select_spinner, getResources().getStringArray(R.array.position));
        position_adapter.setDropDownViewResource(R.layout.custom_spinner);
        position_spinner.setAdapter(position_adapter);

        // post button listener
        database = FirebaseDatabase.getInstance(dburl);
        reference = database.getReference("Article");
        Button postbtn = (Button)findViewById(R.id.postButton);
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // article data
                String productName = ((EditText)findViewById(R.id.productTitle)).getText().toString();
                Integer totalPrice = Integer.parseInt(((EditText)findViewById(R.id.totalPrice)).getText().toString());
                String URL = ((EditText)findViewById(R.id.productLink)).getText().toString();
                String tradePlace = ((Spinner)findViewById(R.id.positionSpinner)).getSelectedItem().toString();
                String category = ((Spinner)findViewById(R.id.categorySpinner)).getSelectedItem().toString();
                Integer productCount = Integer.parseInt(((Spinner)findViewById(R.id.count_spinner)).getSelectedItem().toString());
                String unit = ((Spinner)findViewById(R.id.spinner_unit)).getSelectedItem().toString();
                String content = ((EditText)findViewById(R.id.productContent)).getText().toString();
                String uid = user.getUid(); // change the FirebaseUser user = mAuth.getCurrentUser();

                // Firebase Storage object
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference imgRef = null;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                String articleCode = uid+"_"+sdf.format(new Date());

                ArrayList<String> imgList = new ArrayList<>();

                for (int i = 0; i < imageList.size(); i++) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    StorageReference sRef = storage.getReference("article/image/"+articleCode+"/"+imageList.get(i).toString());
                    Uri imgUri = getImageUri(getApplicationContext(),imageList.get(i));
                    imgList.add("article/image/"+articleCode+"/"+imageList.get(i).toString());
                    sRef.putFile(imgUri);
                }

                Article article = new Article(articleCode, productName, totalPrice, URL, tradePlace, category, productCount, unit, content, uid, imgList);
                reference.push().setValue(article);
            }
        });
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //다이얼로그 생성
    private void makeDialog() {
        //권한 체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.") //권한 팝업창
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check();
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
            Bitmap bitmap = null;
            al_photoUri = data.getData();
            ClipData clipData = data.getClipData();
            if(clipData == null){
                String imagePath = getRealPathFromURI(al_photoUri);

                ExifInterface exif = null;

                try {
                    exif = new ExifInterface(imagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int exifOrientation;
                int exifDegree;

                if (exif != null) {
                    exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    exifDegree = exifOrientationToDegrees(exifOrientation);
                } else {
                    exifDegree = 0;
                }
                bitmap = BitmapFactory.decodeFile(imagePath);
                bitmap = rotate(bitmap, exifDegree);
                imageList.add(bitmap);
            }
            else if((clipData.getItemCount() + count) >10){
                Toast.makeText(getApplicationContext(),"10개 이상 선택되었습니다.",Toast.LENGTH_SHORT).show();
            }
            else {
                int num = clipData.getItemCount();
                for (int i = 0; i < num; i++) {
                    String imagePath = getRealPathFromURI(clipData.getItemAt(i).getUri());

                    ExifInterface exif = null;

                    try {
                        exif = new ExifInterface(imagePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int exifOrientation;
                    int exifDegree;

                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }
                    bitmap = BitmapFactory.decodeFile(imagePath);
                    bitmap = rotate(bitmap, exifDegree);
                    imageList.add(bitmap);
                }
            }
            setImage();
        }
        else if(requestCode==FROM_CAMERA && resultCode==RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);

            ExifInterface exif = null;

            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            } else {
                exifDegree = 0;
            }
            bitmap = rotate(bitmap,exifDegree);
            imageList.add(bitmap);
            setImage();
        }
    }

    //사진 절대경로
    private String getRealPathFromURI(Uri uri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri,proj,null,null,null);

        if(cursor.moveToFirst()){
            column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    //회전각 구하기
    private int exifOrientationToDegrees(int exifOrientation) {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
            return 90;
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
            return 180;
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
            return 270;
        return 0;
    }

    //회전
    private Bitmap rotate(Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(), bitmap.getHeight(),matrix,true);
    }

    //이미지 업로드
    private void setImage() {
        for(int i=0; i< imageList.size();i++){
            Bitmap bitmap = imageList.get(i);
            switch (i){
                case 0:
                    iv_result1.setImageBitmap(bitmap);
                    iv_result1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    iv_result2.setImageBitmap(bitmap);
                    iv_result2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    iv_result3.setImageBitmap(bitmap);
                    iv_result3.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    iv_result4.setImageBitmap(bitmap);
                    iv_result4.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    iv_result5.setImageBitmap(bitmap);
                    iv_result5.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    iv_result6.setImageBitmap(bitmap);
                    iv_result6.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    iv_result7.setImageBitmap(bitmap);
                    iv_result7.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    iv_result8.setImageBitmap(bitmap);
                    iv_result8.setVisibility(View.VISIBLE);
                    break;
                case 8:
                    iv_result9.setImageBitmap(bitmap);
                    iv_result9.setVisibility(View.VISIBLE);
                    break;
                case 9:
                    iv_result10.setImageBitmap(bitmap);
                    iv_result10.setVisibility(View.VISIBLE);
                    break;
            }
        }
        count = imageList.size();
    }

    PermissionListener permissionListener =new PermissionListener() {
        @Override
        //권한 허용했을 때
        public void onPermissionGranted() {
            //Toast.makeText(getApplicationContext(),"권한이 허용됨",Toast.LENGTH_SHORT).show();
        }

        @Override
        //권한을 거부했을 때
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(),"권한이 거부됨",Toast.LENGTH_SHORT).show();

        }
    };
}