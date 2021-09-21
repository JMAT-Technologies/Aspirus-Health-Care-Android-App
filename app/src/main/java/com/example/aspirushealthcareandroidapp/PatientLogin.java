package com.example.aspirushealthcareandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PatientLogin extends AppCompatActivity {

    EditText et_email;
    EditText et_password;
    TextView tv_loginSignUp;
    Button btn_login;
    FloatingActionButton fab_google;
    FloatingActionButton fab_facebook;
    FloatingActionButton fab_twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        et_email       = findViewById(R.id.login_email);
        et_password    = findViewById(R.id.login_password);
        tv_loginSignUp = findViewById(R.id.tv_loginsignup);
        btn_login      = findViewById(R.id.btn_signin);
        fab_google     = findViewById(R.id.fab_google);
        fab_facebook   = findViewById(R.id.fab_facebook);
        fab_twitter    = findViewById(R.id.fab_twitter);

        //link to sign up screen
        tv_loginSignUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), PatientSignUp.class));
            }
        });

        //sign in function
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SignIn();
            }
        });

        //google login in function
        fab_google.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                googleSignIn();
            }
        });

        //facebook login in function
        fab_facebook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                facebookSignIn();
            }
        });

        //twitter login in function
        fab_twitter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                twitterSignIn();
            }
        });
    }

    public void SignIn(){
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();


    }

    public void googleSignIn(){

    }

    public void facebookSignIn(){

    }

    public void twitterSignIn(){

    }
}