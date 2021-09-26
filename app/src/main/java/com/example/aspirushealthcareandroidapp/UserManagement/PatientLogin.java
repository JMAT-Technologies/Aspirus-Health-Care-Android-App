package com.example.aspirushealthcareandroidapp.UserManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.MainActivity;
import com.example.aspirushealthcareandroidapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PatientLogin extends AppCompatActivity {

    EditText et_email;
    EditText et_password;
    TextView tv_loginSignUp;
    Button btn_login;
    FloatingActionButton fab_google;
    FloatingActionButton fab_facebook;
    FloatingActionButton fab_twitter;
    ImageView btn_doc_login;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //linking the views
        et_email       = findViewById(R.id.login_email);
        et_password    = findViewById(R.id.login_password);
        tv_loginSignUp = findViewById(R.id.tv_loginsignup);
        btn_login      = findViewById(R.id.btn_signin);
        fab_google     = findViewById(R.id.fab_google);
        fab_facebook   = findViewById(R.id.fab_facebook);
        fab_twitter    = findViewById(R.id.fab_twitter);
        btn_doc_login  = findViewById(R.id.btn_doc_login);

        //animations for floating action buttons
        fab_google.setTranslationY(300);
        fab_facebook.setTranslationY(300);
        fab_twitter.setTranslationY(300);
        fab_google.setAlpha(0F);
        fab_facebook.setAlpha(0F);
        fab_twitter.setAlpha(0F);
        fab_google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        fab_facebook.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        fab_twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();

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

        //doctor login in function
        btn_doc_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), DoctorLogin.class));
            }
        });
    }

    //check whether an user is already logged in
    @Override
    protected void onStart(){
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void SignIn(){
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if(email.isEmpty()){
            et_email.setError("Required");
            return;
        } else {
            et_email.setError(null);
        }

        if(password.isEmpty()){
            et_password.setError("Required");
            return;
        } else {
            et_password.setError(null);
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PatientLogin.this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void googleSignIn(){

    }

    public void facebookSignIn(){

    }

    public void twitterSignIn(){

    }
}