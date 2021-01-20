package com.example.comehere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;

    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;

    private Drawable pwcheckDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.registerButton).setOnClickListener(onClickListener);

        // Student card Image code
        imageView = (ImageView)findViewById(R.id.studentCardImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        // Underline
        TextView textView = (TextView)findViewById(R.id.loginButton);
        SpannableString content = new SpannableString("로그인하기");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0); textView.setText(content);

        // login button click listener
        TextView loginButoon = (TextView)findViewById(R.id.loginButton);
        loginButoon.setOnClickListener(new View.OnClickListener() {
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

        // password check
        final EditText pwcheck = (EditText)findViewById(R.id.passwordCheckText);
        final EditText pw = (EditText)findViewById(R.id.passwordText);
        final ImageView setImage = (ImageView)findViewById(R.id.pwImage);

        pwcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pw.getText().toString().equals(pwcheck.getText().toString())) {
                    setImage.setImageResource(R.drawable.pwok);
                }else {
                    setImage.setImageResource(R.drawable.pwno);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    // register button click listener
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.registerButton:
                    signUp();
                    break;
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
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

                                    // User object save in firebase
                                    User inputUser = new User(uid, nickname, school, sId);
                                    FirebaseDatabase database= FirebaseDatabase.getInstance();
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
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    startToast(nickname + "님의 가입을 환영합니다!.");

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

                                        startToast(task.getException().toString());
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
