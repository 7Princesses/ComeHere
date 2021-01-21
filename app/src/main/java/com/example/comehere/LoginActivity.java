package com.example.comehere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import kotlin.jvm.internal.Ref;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth기
        mAuth = FirebaseAuth.getInstance();

        // Underline
        TextView textView = (TextView)findViewById(R.id.registerButton);
        SpannableString content = new SpannableString("회원가입하기");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0); textView.setText(content);

        // register button click listner
        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        // login button click listener
        TextView loginButton = (TextView)findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    private void signIn() {
        String email = ((EditText) findViewById(R.id.emailText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();

        if (email.length() > 0 && password.length() > 0) {
            // password same password check
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                final String currUID = user.getUid();
                                String nickname = user.getDisplayName();

                                // UI logic success
                                startToast(nickname + "님 \n여기붙어 접속을 환영합니다!");
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivityForResult(intent, 101);
                                finish();
                            } else {
                                if (task.getException() != null) {
                                    try {
                                        throw task.getException();
                                    }catch (FirebaseAuthInvalidCredentialsException e) {
                                        // 비밀번호 일치x
                                        startToast("비밀번호가 일치하지 않습니다.");
                                    }catch (FirebaseAuthInvalidUserException e) {
                                        // 없는 이메일
                                        startToast("존재하지 않는 이메일입니다.");
                                    }catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    // If sign in fails, display a message to the user.
//                                    startToast(task.getException().toString());
                                }
                            }
                        }
                    });
        }else {
            startToast("이메일 또는 비밀번호를 입력해주세요.");
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
