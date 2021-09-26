package com.example.aspirushealthcareandroidapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Channeling extends AppCompatActivity{

    EditText inputSearch;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<DoctorModel> options;
    FirebaseRecyclerAdapter<DoctorModel, ViewHolderDoctorList>adapter;
    DatabaseReference DoctorRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channeling);

        DoctorRef = FirebaseDatabase.getInstance().getReference().child("TestDoctor");

        inputSearch = findViewById(R.id.inputSearch);
        recyclerView = findViewById(R.id.appointmentRecyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        LoadData();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId((R.id.channelingpage));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homepage:
                        startActivity(new Intent(getApplicationContext()
                                ,Homepage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.channelingpage:
                        return true;

                    case R.id.pharmacypage:
                        startActivity(new Intent(getApplicationContext()
                                ,Appointment.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }

    private void LoadData() {

        options = new FirebaseRecyclerOptions.Builder<DoctorModel>().setQuery(DoctorRef, DoctorModel.class).build();
        adapter = new FirebaseRecyclerAdapter<DoctorModel, ViewHolderDoctorList>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderDoctorList holder, @SuppressLint("RecyclerView") int position, @NonNull DoctorModel model) {
                holder.name.setText(model.getName());
                holder.speciality.setText(model.getSpeciality());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                //onclick
                holder.view.setOnClickListener(new View.OnClickListener(){
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
            public ViewHolderDoctorList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_item, parent,false);
                return new ViewHolderDoctorList(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

}