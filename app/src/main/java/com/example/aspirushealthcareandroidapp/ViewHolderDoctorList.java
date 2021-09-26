package com.example.aspirushealthcareandroidapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderDoctorList extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView name;
    TextView speciality;
    View view;

    public ViewHolderDoctorList(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.doctor_image);
        name = (TextView)itemView.findViewById(R.id.doctor_name);
        speciality = (TextView)itemView.findViewById(R.id.name_speciality);
        view=itemView;
    }
}
