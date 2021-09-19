package com.example.aspirushealthcareandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class PatientSignUp extends AppCompatActivity {

    DatePickerDialog calendar;
    TextInputLayout et_username;
    TextInputLayout et_email;
    TextInputLayout et_phone;
    TextInputLayout et_dob;
    RadioButton rbtn_male;
    RadioButton rbtn_female;
    TextInputLayout et_password;
    TextInputLayout et_confirmPassword;
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);

        et_username = findViewById(R.id.signup_username);
        et_email = findViewById(R.id.signup_email);
        et_phone = findViewById(R.id.signup_phone);
        et_dob = findViewById(R.id.signup_dob);
        rbtn_male = findViewById(R.id.rbtn_male);
        rbtn_female = findViewById(R.id.rbtn_female);
        et_password = findViewById(R.id.signup_password);
        et_confirmPassword = findViewById(R.id.signup_confirm_pw);
        btn_signup = findViewById(R.id.btn_signup);

        //date picker popup
        et_dob.getEditText().setInputType(InputType.TYPE_NULL);
        et_dob.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                calendar = new DatePickerDialog(PatientSignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        et_dob.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                calendar.show();
            }
        });
    }

}