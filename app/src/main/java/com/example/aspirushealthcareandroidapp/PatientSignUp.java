package com.example.aspirushealthcareandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

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


        //sign up function
        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SignUp();
            }
        });

    }

    public void SignUp(){
        String username = et_username.getEditText().getText().toString();
        String email = et_email.getEditText().getText().toString();
        String phone = et_phone.getEditText().getText().toString();
        String dob = et_dob.getEditText().getText().toString();
        String password = et_password.getEditText().getText().toString();
        String confirmPassword = et_confirmPassword.getEditText().getText().toString();
        String gender;
        if(rbtn_male.isChecked()){
            gender = "male";
        } else if(rbtn_female.isChecked()) {
            gender = "female";
        } else if (!rbtn_female.isChecked() | !rbtn_male.isChecked()){
            rbtn_female.setError("Require");
            rbtn_male.setError("Require");
        } else {
            rbtn_male.setError(null);
            rbtn_female.setError(null);
        }

        if(username.isEmpty()){
            et_username.setError("Required");
        }else{
            et_username.setError(null);
            et_username.setErrorEnabled(false);
        }

        if(dob.isEmpty()){
            et_dob.setError("Required");
        } else {
            et_dob.setError(null);
            et_dob.setErrorEnabled(false);
        }

        if(!UserValidations.emailValidate(email)){
            et_email.setError("This must be an email");
        }else {
            et_email.setError(null);
            et_email.setErrorEnabled(false);
        }

        if(!UserValidations.phoneValidate(phone)){
            et_phone.setError("This must be a phone number");
        }else {
            et_phone.setError(null);
            et_phone.setErrorEnabled(false);
        }

        if(!UserValidations.passwordValidate(password)){
            et_password.setError("Password must contain upper and lower case letters and numbers");
        }else {
            et_password.setError(null);
            et_password.setErrorEnabled(false);
        }

        if(!UserValidations.passwordValidate(confirmPassword)){
            et_confirmPassword.setError("Password must contain upper and lower case letters and numbers");
        }else {
            et_confirmPassword.setError(null);
            et_confirmPassword.setErrorEnabled(false);
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(this,"Password and Confirm Password must be equal", Toast.LENGTH_SHORT);
        }
    }

}