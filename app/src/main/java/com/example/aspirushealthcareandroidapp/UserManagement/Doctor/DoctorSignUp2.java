package com.example.aspirushealthcareandroidapp.UserManagement.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.Homepage;
import com.example.aspirushealthcareandroidapp.R;
import com.example.aspirushealthcareandroidapp.UserManagement.UserValidations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DoctorSignUp2 extends AppCompatActivity {

    ImageView profile_pic;
    TextInputLayout et_nic;
    TextInputLayout et_slmc;
    TextInputLayout et_speciality;
    TextInputLayout et_work;
    TextInputLayout et_fee;
    Button btn_next;

    String userID;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up2);

        userID = getIntent().getStringExtra(DoctorSignUp.EXTRA_DOCTORID);

        profile_pic    = findViewById(R.id.doc_profile_pic);
        et_nic         = findViewById(R.id.doc_signup_nic);
        et_slmc        = findViewById(R.id.doc_signup_slmc);
        et_speciality  = findViewById(R.id.doc_signup_speciality);
        et_work        = findViewById(R.id.doc_signup_work);
        et_fee         = findViewById(R.id.doc_signup_fee);
        btn_next       = findViewById(R.id.doc_btn_signup);

        //sign up function
        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SignUp2();
            }
        });
    }

    private void SignUp2(){
        String nic = et_nic.getEditText().getText().toString();
        String slmc = et_slmc.getEditText().getText().toString();
        String speciality = et_speciality.getEditText().getText().toString();
        String work = et_work.getEditText().getText().toString();
        String fee = et_fee.getEditText().getText().toString();

        if(!UserValidations.nicValidation(et_nic)){
            return;
        }

        if(!UserValidations.slmcValidation(et_slmc)) {
            return;
        }

        if(UserValidations.specialityNull(et_speciality)) {
            return;
        }

        if(UserValidations.workNull(et_work)){
            return;
        }

        if(!UserValidations.feeValidation(et_fee)){
            return;
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("doctors");

        Map<String, Object> doctor = new HashMap< >();
        doctor.put("nic",nic);
        doctor.put("speciality",speciality);
        doctor.put("slmc",slmc);
        doctor.put("workingPlace",work);
        doctor.put("fee",fee);

        //saving data to database
        reference.child(userID).updateChildren(doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DoctorSignUp2.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Homepage.class));
                    finish();
                } else {
                    Toast.makeText(DoctorSignUp2.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}