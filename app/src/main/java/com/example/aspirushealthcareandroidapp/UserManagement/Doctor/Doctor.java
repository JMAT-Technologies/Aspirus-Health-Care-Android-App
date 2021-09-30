package com.example.aspirushealthcareandroidapp.UserManagement.Doctor;

import com.example.aspirushealthcareandroidapp.UserManagement.Person;

public class Doctor extends Person {
    private String speciality;
    private String workingPlace;
    private String fee;

    public Doctor() {
    }

    public Doctor(String userID, String username, String email, String phone, String dob, int age, String gender, String password, String image, String speciality,
                  String workingPlace, String fee) {
        super(userID, username, email, phone, dob, age, gender, password, image);
        this.speciality = speciality;
        this.workingPlace = workingPlace;
        this.fee = fee;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
