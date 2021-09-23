package com.example.aspirushealthcareandroidapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView name;
    TextView speciality;
    View v;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.doctor_image);
        name = (TextView)itemView.findViewById(R.id.doctor_name);
        speciality = (TextView)itemView.findViewById(R.id.name_speciality);
        v=itemView;
    }
}
