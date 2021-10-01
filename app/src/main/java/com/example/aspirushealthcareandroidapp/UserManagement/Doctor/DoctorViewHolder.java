package com.example.aspirushealthcareandroidapp.UserManagement.Doctor;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aspirushealthcareandroidapp.R;

public class DoctorViewHolder extends RecyclerView.ViewHolder {

    public ImageView doctor_image;
    public TextView username,speciality;
    public View doctorView;


    public DoctorViewHolder(@NonNull View view) {
        super(view);

        doctor_image = view.findViewById(R.id.doctor_card_image);
        username =  view.findViewById(R.id.doc_card_username);
        speciality = view.findViewById(R.id.doc_card_speciality);
        doctorView = view;
    }
}
