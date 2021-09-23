package com.example.aspirushealthcareandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.load.model.ModelLoader;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Channeling extends AppCompatActivity{

    EditText inputSearch;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<DoctorModel> options;
    FirebaseRecyclerAdapter<DoctorModel, MyViewHolder>adapter;
    DatabaseReference DoctorRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channeling);

        DoctorRef = FirebaseDatabase.getInstance().getReference().child("TestDoctor");

        inputSearch = findViewById(R.id.inputSearch);
        recyclerView = findViewById(R.id.doctorRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        LoadData();
    }

    private void LoadData() {

        options = new FirebaseRecyclerOptions.Builder<DoctorModel>().setQuery(DoctorRef, DoctorModel.class).build();
        adapter=new FirebaseRecyclerAdapter<DoctorModel, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull DoctorModel model) {
                holder.name.setText(model.getName());
                holder.speciality.setText(model.getSpeciality());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                //onclick
                holder.v.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Channeling.this, ViewDoctorActivity.class);
                        intent.putExtra("DoctorKey", getRef(position).getKey());
                        startActivity(intent);


                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_item, parent,false);
                return new  MyViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

}