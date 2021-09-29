package com.example.aspirushealthcareandroidapp.UserManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
    TextView tv_signupLogin;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    int age;

    public static final String EXTRA_USERID = "com.example.myapplication.userID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);

        et_username        = findViewById(R.id.signup_username);
        et_email           = findViewById(R.id.signup_email);
        et_phone           = findViewById(R.id.signup_phone);
        et_dob             = findViewById(R.id.signup_dob);
        rbtn_male          = findViewById(R.id.rbtn_male);
        rbtn_female        = findViewById(R.id.rbtn_female);
        et_password        = findViewById(R.id.signup_password);
        et_confirmPassword = findViewById(R.id.signup_confirm_pw);
        btn_signup         = findViewById(R.id.doc_btn_signin);
        tv_signupLogin     = findViewById(R.id.tv_signupLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        //date picker popup
        et_dob.getEditText().setInputType(InputType.TYPE_NULL);
        et_dob.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarPopUp();
            }
        });


        //sign up function
        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SignUp();
            }
        });

        //link to login screen
        tv_signupLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), PatientLogin.class));
            }
        });

    }

    public void calendarPopUp(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        calendar = new DatePickerDialog(PatientSignUp.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et_dob.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                age = UserValidations.calculateAge(year);
            }
        }, year, month, day);
        calendar.show();
    }

    private void SignUp(){
        String username = et_username.getEditText().getText().toString();
        String email = et_email.getEditText().getText().toString();
        String phone = et_phone.getEditText().getText().toString();
        String dob = et_dob.getEditText().getText().toString();
        String password = et_password.getEditText().getText().toString();
        String confirmPassword = et_confirmPassword.getEditText().getText().toString();
        String gender = rbtn_male.getText().toString();

        if(UserValidations.usernameNull(et_username)){
            return;
        }

        if(!UserValidations.emailValidate(et_email)) {
            return;
        }

        if(!UserValidations.phoneValidate(et_phone)) {
            return;
        }

        if(UserValidations.dobNull(et_dob)){
            return;
        }

        if(rbtn_male.isChecked()){
            gender = rbtn_male.getText().toString();;
        } else if(rbtn_female.isChecked()) {
            gender = rbtn_female.getText().toString();;
        } else if(UserValidations.genderNull(rbtn_male,rbtn_female)){
            return;
        }
        String finalGender = gender;

        if(!UserValidations.passwordValidate(et_password)){
            return;
        }

        if(!UserValidations.passwordValidate(et_confirmPassword)){
            return;
        }

        if(!password.equals(confirmPassword)) {
            Toast.makeText(PatientSignUp.this, "Password and Confirm Password must be equal", Toast.LENGTH_SHORT).show();
            return;
        }
        
        //registering the user in firebase
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String userID = firebaseAuth.getCurrentUser().getUid();

                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("patients");

                    //creating a patient object with the data
                    Map<String, Object> newPatient = new HashMap< >();
                    newPatient.put("userID",userID);
                    newPatient.put("username",username);
                    newPatient.put("email",email);
                    newPatient.put("phone",phone);
                    newPatient.put("dob",dob);
                    newPatient.put("age",age);
                    newPatient.put("finalGender",finalGender);
                    newPatient.put("password",password);
                    newPatient.put("bloodGroup","null");
                    newPatient.put("bloodPressure","null");
                    newPatient.put("sugarLevel",0);
                    newPatient.put("height",0);
                    newPatient.put("weight",0);
                    newPatient.put("bmi",0);

                    //saving data to database
                    reference.child(userID).setValue(newPatient).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PatientSignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                //redirecting to the profile picture upload
                                Intent profilePicUploadIntent = new Intent(getApplicationContext(), PatientProfilePicUpload.class);
                                profilePicUploadIntent.putExtra(EXTRA_USERID,userID);
                                startActivity(profilePicUploadIntent);
                                finish();
                            } else {
                                Toast.makeText(PatientSignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(PatientSignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}