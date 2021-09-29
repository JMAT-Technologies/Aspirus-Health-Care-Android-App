package com.example.aspirushealthcareandroidapp.UserManagement;

public class Doctor extends Person {
    private String speciality;
    private String workingPlace;
    private double fee;

    public Doctor() {
    }

    public Doctor(String userID, String username, String email, String phone, String dob, int age, String gender, String password, String image, String speciality,
                  String workingPlace, double fee) {
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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
