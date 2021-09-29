package com.example.aspirushealthcareandroidapp.UserManagement;

import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
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

        if(matcher.matches()){
            et_phone.setError(null);
            et_phone.setErrorEnabled(false);
            return true;
        }else {
            et_phone.setError("This must be a phone number");
            return false;
        }
    }

    //checking date of birth is null
    public static boolean dobNull(final  TextInputLayout et_dob){
        String dob = et_dob.getEditText().getText().toString();
        if(dob.isEmpty()){
            et_dob.setError("Required");
            return true;
        }else{
            et_dob.setError(null);
            et_dob.setErrorEnabled(false);
            return false;
        }
    }

    //calculate age
    public static int calculateAge(int year){
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - year;

        return age;
    }

    //checking gender is null
    public static boolean genderNull(final RadioButton rbtn_male, final  RadioButton rbtn_female){

        if (!rbtn_female.isChecked() | !rbtn_male.isChecked()){
            rbtn_female.setError("Required");
            rbtn_male.setError("Required");
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

    //weight validation
    public static boolean weightValidation(final TextInputLayout et_weight) {

        String weight = et_weight.getEditText().getText().toString();

        if(weight.isEmpty()){
            et_weight.setError("Required");
            return false;
        }else if(Double.parseDouble(weight) == 0){
            et_weight.setError("Can't be zero");
            return false;
        } else {
            et_weight.setError(null);
            et_weight.setErrorEnabled(false);
            return true;
        }
    }

    //height validation
    public static boolean heightValidation(final TextInputLayout et_height) {

        String height = et_height.getEditText().getText().toString();

        if(height.isEmpty()){
            et_height.setError("Required");
            return false;
        }else if(Double.parseDouble(height) == 0){
            et_height.setError("Can't be zero");
            return false;
        } else {
            et_height.setError(null);
            et_height.setErrorEnabled(false);
            return true;
        }
    }

    //bmi calculation
    public static double calculateBmi(final String height,final String weight) {

        double dHeight = Double.parseDouble(height);
        double dWeight = Double.parseDouble(weight);
        double result;

        result = dWeight / (dHeight * dWeight);
        return Math.round(result*100.0)/100.0;
    }

    //bloodGroup validation
    public static boolean bloodGroupValidation(final TextInputLayout et_bloodGroup) {

        String bloodGroup = et_bloodGroup.getEditText().getText().toString();

        if(bloodGroup.isEmpty()){
            et_bloodGroup.setError("Required");
            return false;
        } else {
            et_bloodGroup.setError(null);
            et_bloodGroup.setErrorEnabled(false);
            return true;
        }
    }

    //bloodPressure validation
    public static boolean bloodPressureValidation(final TextInputLayout et_bloodPressure) {

        String bloodPressure = et_bloodPressure.getEditText().getText().toString();

        if(bloodPressure.isEmpty()){
            et_bloodPressure.setError("Required");
            return false;
        } else {
            et_bloodPressure.setError(null);
            et_bloodPressure.setErrorEnabled(false);
            return true;
        }
    }

    //sugarLevel validation
    public static boolean sugarLevelValidation(final TextInputLayout et_sugarLevel) {

        String sugarLevel = et_sugarLevel.getEditText().getText().toString();

        if(sugarLevel.isEmpty()){
            et_sugarLevel.setError("Required");
            return false;
        }else if(Double.parseDouble(sugarLevel) == 0){
            et_sugarLevel.setError("Can't be zero");
            return false;
        } else {
            et_sugarLevel.setError(null);
            et_sugarLevel.setErrorEnabled(false);
            return true;
        }
    }

    //these validations are related to doctor
    //bloodPressure validation
    public static boolean nicValidation(final TextInputLayout et_nic) {

        String nic = et_nic.getEditText().getText().toString();

        if(nic.isEmpty()){
            et_nic.setError("Required");
            return false;
        } else {
            et_nic.setError(null);
            et_nic.setErrorEnabled(false);
            return true;
        }
    }

    //slmc validation
    public static boolean slmcValidation(final TextInputLayout et_slmc) {

        String slmc = et_slmc.getEditText().getText().toString();

        if(slmc.isEmpty()){
            et_slmc.setError("Required");
            return false;
        } else {
            et_slmc.setError(null);
            et_slmc.setErrorEnabled(false);
            return true;
        }
    }

    //checking speciality is null
    public static boolean specialityNull(final TextInputLayout et_speciality) {

        String speciality = et_speciality.getEditText().getText().toString();

        if(speciality.isEmpty()){
            et_speciality.setError("Required");
            return true;
        } else {
            et_speciality.setError(null);
            et_speciality.setErrorEnabled(false);
            return false;
        }
    }

    //checking work is null
    public static boolean workNull(final TextInputLayout et_work) {

        String work = et_work.getEditText().getText().toString();

        if(work.isEmpty()){
            et_work.setError("Required");
            return true;
        } else {
            et_work.setError(null);
            et_work.setErrorEnabled(false);
            return false;
        }
    }

    //charge per consultation validation
    public static boolean feeValidation(final TextInputLayout et_fee) {

        String fee = et_fee.getEditText().getText().toString();

        if(fee.isEmpty()){
            et_fee.setError("Required");
            return false;
        }else if(Double.parseDouble(fee) == 0){
            et_fee.setError("Can't be zero");
            return false;
        } else {
            et_fee.setError(null);
            et_fee.setErrorEnabled(false);
            return true;
        }
    }
}