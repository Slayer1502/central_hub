package com.example.nava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginact extends AppCompatActivity {
    TextView tvSignUp;
    EditText emailId, password;
    Button btnSignIn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth =FirebaseAuth.getInstance();
        emailId = findViewById(R.id.mailid);
        password = findViewById(R.id.passwrd);
        btnSignIn = findViewById(R.id.login);
        tvSignUp = findViewById(R.id.textView3);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser =mFirebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Toast.makeText(loginact.this,"you are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(loginact.this,user_logged_main.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(loginact.this,"please Login",Toast.LENGTH_SHORT).show();
                }

            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailId.getText().toString();
                String pwd = password.getText().toString();

                if (email.isEmpty()){
                    emailId.setError("Please enter valid email");
                    emailId.requestFocus();
                }
                else  if (pwd.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(loginact.this,"Fields are Empty!",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(loginact.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(loginact.this,"Login ERROR ReTry",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intToHome = new Intent(loginact.this,user_logged_main.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(loginact.this,"ERROR OccurRed",Toast.LENGTH_SHORT).show();
                }

            }
        });



        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToSignUp = new Intent(loginact.this,user_reg.class);
                startActivity(intToSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }
}
