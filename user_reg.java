package com.example.nava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class user_reg extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignup;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.mailid);
        password = findViewById(R.id.passwrd);
        btnSignup = findViewById(R.id.signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(user_reg.this,"Fields are Empty!",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(user_reg.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(user_reg.this,"Registration UnSuccessful, Please Retry!",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                startActivity(new Intent(user_reg.this,user_logged_main.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(user_reg.this,"ERROR OccurRed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
