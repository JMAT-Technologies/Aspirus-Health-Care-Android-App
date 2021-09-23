package com.example.aspirushealthcareandroidapp;

public class DoctorModel {

    String name, speciality, image;

    DoctorModel(){

    }

    public DoctorModel(String name, String speciality, String image) {
        this.name = name;
        this.speciality = speciality;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
