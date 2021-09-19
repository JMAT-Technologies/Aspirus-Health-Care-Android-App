package com.example.aspirushealthcareandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class PatientSignUp extends AppCompatActivity {

    DatePickerDialog calendar;
    TextInputLayout username;
    TextInputLayout email;
    TextInputLayout phone;
    TextInputLayout dob;
    RadioButton male;
    RadioButton female;
    TextInputLayout password;
    TextInputLayout confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);

        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        phone = findViewById(R.id.signup_phone);
        dob = findViewById(R.id.signup_dob);
        male = findViewById(R.id.rbtn_male);
        female = findViewById(R.id.rbtn_female);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_confirm_pw);

        //date picker popup
        dob.getEditText().setInputType(InputType.TYPE_NULL);
        dob.getEditText().setOnClickListener(new View.OnClickListener() {
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
                        dob.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                calendar.show();
            }
        });
    }

}