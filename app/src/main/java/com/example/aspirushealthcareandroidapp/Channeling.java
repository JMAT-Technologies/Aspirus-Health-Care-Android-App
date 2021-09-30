package com.example.aspirushealthcareandroidapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Channeling extends AppCompatActivity{

    EditText inputSearch;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Doctor> options;
    FirebaseRecyclerAdapter<Doctor, DoctorViewHolder>adapter;
    DatabaseReference DoctorRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channeling);

        //Navigation
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId((R.id.channelingpage));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homepage:
                        startActivity(new Intent(getApplicationContext(), Homepage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pharmacypage:
                        startActivity(new Intent(getApplicationContext() , Pharmacy.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.channelingpage:
                        return true;
                    case R.id.cartpage:
                        startActivity(new Intent(getApplicationContext() , CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profilepage:
                        startActivity(new Intent(getApplicationContext() , PatientProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        DoctorRef = FirebaseDatabase.getInstance().getReference().child("doctors");

        inputSearch = findViewById(R.id.inputSearch);
        recyclerView = findViewById(R.id.appointmentRecyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        LoadData("");

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().toLowerCase()!=null){
                    LoadData(editable.toString());
                }
                else {
                    LoadData("");
                }
            }
        });
    }

    private void LoadData(String data) {
        Query query = DoctorRef.orderByChild("username").startAt(data).endAt(data+"\uf8ff");


        options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(query,Doctor.class).build();
        adapter = new FirebaseRecyclerAdapter<Doctor, DoctorViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Doctor model) {
                holder.username.setText(model.getUsername());
                holder.speciality.setText(model.getSpeciality());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                //onclick
                holder.view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Channeling.this, ViewDoctor.class);
                        intent.putExtra("DoctorKey", getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_item, parent,false);
                return new DoctorViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    public void HomePage(View view) {
        Intent intent = new Intent(this,Homepage.class);
        startActivity(intent);
    }

}