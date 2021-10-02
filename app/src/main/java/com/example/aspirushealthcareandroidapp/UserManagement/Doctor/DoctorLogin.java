package com.example.aspirushealthcareandroidapp.UserManagement.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aspirushealthcareandroidapp.MainActivity;
import com.example.aspirushealthcareandroidapp.R;
import com.example.aspirushealthcareandroidapp.UserManagement.Patient.PatientLogin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorLogin extends AppCompatActivity {

    EditText doc_login_email;
    EditText doc_login_password;
    TextView doc_loginsignup;
    TextView btn_patient_login;
    Button doc_btn_signin;
    Button btn_admin_login;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //linking the views
        doc_login_email       = findViewById(R.id.doc_login_email);
        doc_login_password    = findViewById(R.id.doc_login_password);
        doc_loginsignup       = findViewById(R.id.doc_loginsignup);
        btn_patient_login     = findViewById(R.id.doc_patientLogin);
        doc_btn_signin        = findViewById(R.id.doc_btn_signin);
        btn_admin_login        = findViewById(R.id.btn_admin_login);

        //link to patient login screen
        btn_patient_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), PatientLogin.class));
            }
        });

        //link to admin login screen
        btn_admin_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            }
        });

        //link to sign up screen
        doc_loginsignup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), DoctorSignUp.class));
            }
        });

        //sign in function
        doc_btn_signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SignIn();
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

    private void SignIn(){
        String email = doc_login_email.getText().toString();
        String password = doc_login_password.getText().toString();

        if(email.isEmpty()){
            doc_login_email.setError("Required");
            return;
        } else {
            doc_login_email.setError(null);
        }

        if(password.isEmpty()){
            doc_login_password.setError("Required");
            return;
        } else {
            doc_login_password.setError(null);
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(new Intent(getApplicationContext(), DoctorProfile.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DoctorLogin.this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}