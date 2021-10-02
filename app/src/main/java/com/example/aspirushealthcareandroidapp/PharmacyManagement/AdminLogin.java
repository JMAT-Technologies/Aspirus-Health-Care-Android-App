package com.example.aspirushealthcareandroidapp.PharmacyManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aspirushealthcareandroidapp.R;
import com.example.aspirushealthcareandroidapp.UserManagement.Doctor.DoctorLogin;
import com.example.aspirushealthcareandroidapp.UserManagement.Patient.PatientLogin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {
    EditText admin_login_email;
    EditText admin_login_password;
    TextView adminpagepatient,adminpagedoc;
    Button admin_btn_signin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        firebaseAuth = FirebaseAuth.getInstance();

        //linking the views
        admin_login_email       = findViewById(R.id.admin_login_email);
        admin_login_password    = findViewById(R.id.admin_login_password);
        adminpagepatient     = findViewById(R.id.adminpagepatient);
        adminpagedoc     = findViewById(R.id.adminpagedoc);
        admin_btn_signin        = findViewById(R.id.admin_btn_signin);

        //link to patient login screen
        adminpagepatient.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), PatientLogin.class));
            }
        });
        //link to doctor page login screen
        adminpagedoc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), DoctorLogin.class));
            }
        });


        //sign in function
        admin_btn_signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SignIn();
            }
        });
    }

    //check whether an admin is already logged in
    @Override
    protected void onStart(){
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), AdminPharmacy.class));
            finish();
        }
    }

    private void SignIn(){
        String email = admin_login_email.getText().toString();
        String password = admin_login_password.getText().toString();

        if(email.isEmpty()){
            admin_login_email.setError("Required");
            return;
        } else {
            admin_login_email.setError(null);
        }

        if(password.isEmpty()){
            admin_login_password.setError("Required");
            return;
        } else {
            admin_login_password.setError(null);
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(new Intent(getApplicationContext(), AdminPharmacy.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminLogin.this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

