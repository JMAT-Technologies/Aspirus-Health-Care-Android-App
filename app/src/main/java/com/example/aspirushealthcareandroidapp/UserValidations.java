package com.example.aspirushealthcareandroidapp;

import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidations {

    //check username is null
    public static boolean usernameNull(final  TextInputLayout et_username){
        String username = et_username.getEditText().getText().toString();
        if(username.isEmpty()){
            et_username.setError("Required");
            return true;
        }else{
            et_username.setError(null);
            et_username.setErrorEnabled(false);
            return false;
        }
    }

    //email validation
    public static boolean emailValidate(final TextInputLayout et_email) {

        String email = et_email.getEditText().getText().toString();

        final String EMAIL_PATTERN = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            et_email.setError("This must be an email");
            return false;
        }else {
            et_email.setError(null);
            et_email.setErrorEnabled(false);
            return true;
        }
    }

    //phone validation
    public static boolean phoneValidate(final TextInputLayout et_phone) {

        String phone = et_phone.getEditText().getText().toString();

        final String PHONE_PATTERN = "^(?:7|0|(?:\\+94))[0-9]{9,10}$";

        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);

        if(!matcher.matches()){
            et_phone.setError("This must be a phone number");
            return false;
        }else {
            et_phone.setError(null);
            et_phone.setErrorEnabled(false);
            return true;
        }
    }

    //checking date of birth is null
    public static boolean dobNull(final  TextInputLayout et_dob){
        String username = et_dob.getEditText().getText().toString();
        if(username.isEmpty()){
            et_dob.setError("Required");
            return true;
        }else{
            et_dob.setError(null);
            et_dob.setErrorEnabled(false);
            return false;
        }
    }

    //checking gender is null
    public static boolean genderNull(final RadioButton rbtn_male, final  RadioButton rbtn_female){

        if (!rbtn_female.isChecked() | !rbtn_male.isChecked()){
            rbtn_female.setError("Require");
            rbtn_male.setError("Require");
            return true;
        } else {
            rbtn_male.setError(null);
            rbtn_female.setError(null);
            return false;
        }
    }

    //password validation
    public static boolean passwordValidate(final TextInputLayout et_password) {

        String password = et_password.getEditText().getText().toString();

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()){
            et_password.setError("Password must contain upper and lower case letters and numbers");
            return false;
        }else {
            et_password.setError(null);
            et_password.setErrorEnabled(false);
            return true;
        }
    }

}
