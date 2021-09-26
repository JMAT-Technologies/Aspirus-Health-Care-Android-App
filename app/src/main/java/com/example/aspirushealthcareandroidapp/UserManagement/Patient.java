package com.example.aspirushealthcareandroidapp.UserManagement;

public class Patient extends Person {
    private double sugarLevel;
    private double bloodPressure;
    private String bloodGroup;
    private double height;
    private double weight;
    private double bmi;

    public Patient() {
        super();
    }

    public Patient(String userID, String username, String email, String phone, String dob, int age, String gender, String password) {
        super(userID, username, email, phone, dob, age, gender, password);
        this.sugarLevel = 0;
        this.bloodPressure = 0;
        this.bloodGroup = "null";
        this.height = 0;
        this.weight = 0;
        this.bmi = 0;
    }

    public double getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(double sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public double getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(double bloodPressure) {
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
