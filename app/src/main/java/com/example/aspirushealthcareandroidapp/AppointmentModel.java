package com.example.aspirushealthcareandroidapp;

public class AppointmentModel {

    String Date;
    String Time;
    String DoctorName;

    public AppointmentModel(String date, String time, String doctorName ) {
        Date = date;
        Time = time;
        DoctorName = doctorName;
    }

    public String getDate() {

        return Date;
    }

    public String getTime() {

        return Time;
    }

    public String getDoctorName() {
        return DoctorName;
    }
}
