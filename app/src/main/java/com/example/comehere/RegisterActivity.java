package com.example.comehere;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;

    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;

    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
    private User inputUser;

    private Dialog dialog;
    private Button ok_btn;
    private TextView messageTv;

    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Student card Image code
        imageView = (ImageView)findViewById(R.id.studentCardImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        // Underline in login TextView
        TextView textView = (TextView)findViewById(R.id.loginButton);
        SpannableString content = new SpannableString("로그인하기");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0); textView.setText(content);

        // login button click listener
        TextView loginButton = (TextView)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(loginIntent);
            }
        });

        // school spinner
        spinner = (Spinner)findViewById(R.id.schoolSpinner);
        adapter = new ArrayAdapter<String>(this, R.layout.select_spinner, getResources().getStringArray(R.array.school));
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner.setAdapter(adapter);

        // password check alarm
        final EditText pwcheck = (EditText)findViewById(R.id.passwordCheckText);
        final EditText pw = (EditText)findViewById(R.id.passwordText);
        final ImageView setImage = (ImageView)findViewById(R.id.pwImage);
        setImage.setImageResource(R.drawable.pwno);
        pwcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pw.getText().toString().equals(pwcheck.getText().toString())) {
                    // password and password check is same
                    setImage.setImageResource(R.drawable.pwok);
                }else {
                    // password and password check is not same
                    setImage.setImageResource(R.drawable.pwno);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pw.getText().toString().equals(pwcheck.getText().toString())) {
                    // password and password check is same
                    setImage.setImageResource(R.drawable.pwok);
                }else {
                    // password and password check is not same
                    setImage.setImageResource(R.drawable.pwno);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // register button click listener
        Button register_btn = (Button)findViewById(R.id.registerButton);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        dialog = new Dialog(this);
    }

    public void ShowOkPopup() {
        dialog.setContentView(R.layout.epic_popup);
        messageTv = (TextView)dialog.findViewById(R.id.dialogMessageText);
        ok_btn = (Button)dialog.findViewById(R.id.okbtn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
//                finish();
                startToast(inputUser.getNickname() + "님의 가입을 환영합니다!.");
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setMessage("회원가입 완료!\n이제 우리 학교 사람들과\n공동구매를 시작해보세요");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                startToast(inputUser.getNickname() + "님의 가입을 환영합니다!");
            }
        });

        builder.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            imageView.setImageURI(imgUri);
        }

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    // select image dir get
                    imgUri = data.getData();
                    imageView.setImageURI(imgUri);
                    Glide.with(this).load(imgUri).into(imageView);
                }
                break;
        }
    }

    // sign up function
    private void signUp() {
        String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckText)).getText().toString();

        if (email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {
            if (password.equals(passwordCheck)) {
                // password same password check
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // User data
                                    String uid = user.getUid();
                                    String nickname = ((EditText)findViewById(R.id.nicknameText)).getText().toString().trim();
                                    String school = ((Spinner)findViewById(R.id.schoolSpinner)).getSelectedItem().toString();
                                    Integer sId = Integer.parseInt(((EditText)findViewById(R.id.studentIdText)).getText().toString().trim());

                                    // Firebase Storage object
                                    FirebaseStorage storage = FirebaseStorage.getInstance();

                                    StorageReference imgRef = null;

                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                                    String filename = nickname+sdf.format(new Date())+".png";
                                    String studentIdCard = "studentCard/"+filename;

                                    StorageReference sRef = storage.getReference("studentCard/"+filename);
                                    sRef.putFile(imgUri);

                                    // User object save in firebase
                                    inputUser = new User(uid, nickname, school, sId, studentIdCard);
                                    FirebaseDatabase database= FirebaseDatabase.getInstance(dburl);
                                    DatabaseReference reference = database.getReference("User");
                                    reference.child(uid).setValue(inputUser);

                                    // set current user profile
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nickname) // if you want add the profile photo
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User profile updated");
                                                    }
                                                }
                                            });

                                    // UI Logic when success
                                    ShowOkPopup();
//                                    Dialog();
                                } else {
                                    if (task.getException() != null) {
                                        // If sign in fails, display a message to the user.
                                        try {
                                            throw task.getException();
                                        }catch (FirebaseAuthUserCollisionException e) {
                                            startToast("이미 존재하는 이메일입니다.");
                                            findViewById(R.id.emailText).requestFocus();
                                        } catch (FirebaseAuthWeakPasswordException e) {
                                            startToast("비밀번호를 6자리 이상 입력해주세요.");
                                            findViewById(R.id.passwordText).requestFocus();
                                        } catch (Exception e) {
                                            startToast(e.toString());
                                        }

//                                        startToast(task.getException().toString());
                                        // UI Logic when failed
                                    }
                                }
                            }
                        });

            }else{
                // password not same passwordcheck
                startToast("비밀번호가 일치하지 않습니다.");
            }
        }else {
            startToast("이메일 또는 비밀번호를 입력해주세요.");
        }

    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
