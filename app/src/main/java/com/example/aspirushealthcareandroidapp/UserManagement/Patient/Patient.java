package com.example.aspirushealthcareandroidapp.UserManagement.Patient;

import com.example.aspirushealthcareandroidapp.UserManagement.Person;

public class Patient extends Person {
    private double sugarLevel;
    private String bloodPressure;
    private String bloodGroup;
    private double height;
    private double weight;
    private double bmi;

    public Patient() {
        super();
    }

    public Patient(String userID, String username, String email, String phone, String dob, int age, String gender, String password, String image, double sugarLevel,
                   String bloodPressure, String bloodGroup, double height, double weight, double bmi) {
        super(userID, username, email, phone, dob, age, gender, password, image);
        this.sugarLevel = sugarLevel;
        this.bloodPressure = bloodPressure;
        this.bloodGroup = bloodGroup;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
    }

    public double getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(double sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }
}
