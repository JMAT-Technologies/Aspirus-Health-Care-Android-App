package com.example.aspirushealthcareandroidapp;

public class AppointmentModel {

    String date;
    String time;
    String doctorName;

    public AppointmentModel() {
    }

    public AppointmentModel(String date, String time, String doctorName) {
        this.date = date;
        this.time = time;
        this.doctorName = doctorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
