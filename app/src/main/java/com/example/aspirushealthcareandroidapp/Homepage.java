package com.example.aspirushealthcareandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.aspirushealthcareandroidapp.AppointmentManagement.Channeling;
import com.example.aspirushealthcareandroidapp.AppointmentManagement.ViewDoctor;
import com.example.aspirushealthcareandroidapp.PharmacyManagement.Pharmacy;
import com.example.aspirushealthcareandroidapp.UserManagement.Doctor.Doctor;
import com.example.aspirushealthcareandroidapp.UserManagement.Doctor.DoctorViewHolder;
import com.example.aspirushealthcareandroidapp.UserManagement.Patient.PatientLogin;
import com.example.aspirushealthcareandroidapp.UserManagement.Patient.PatientProfile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Homepage extends AppCompatActivity {

    EditText doctor_search;
    RecyclerView doctor_recycle_view;
    FirebaseRecyclerOptions<Doctor> options; //pass the model class
    FirebaseRecyclerAdapter<Doctor, DoctorViewHolder> adapter;
    DatabaseReference DoctorReference;

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), PatientLogin.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        DoctorReference = FirebaseDatabase.getInstance().getReference().child("doctors");
        doctor_search = findViewById(R.id.doctor_search);
        doctor_recycle_view = (RecyclerView) findViewById(R.id.doctor_recycle_view);

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(Homepage.this, LinearLayoutManager.HORIZONTAL, false);
        doctor_recycle_view.setLayoutManager(horizontalLayoutManagaer);

        //this code is for vertical recycle view
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
//        doctor_recycle_view.setLayoutManager(gridLayoutManager);

        doctor_recycle_view.setHasFixedSize(true);

        // search function in the load data method
        LoadData("");
        doctor_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString()!=null){
                    LoadData(editable.toString());
                }
                else {
                    LoadData("");
                }
            }
        });


        //navigation bar
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId((R.id.homepage));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homepage:
                        return true;
                    case R.id.pharmacypage:
                        startActivity(new Intent(getApplicationContext() , Pharmacy.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.channelingpage:
                        startActivity(new Intent(getApplicationContext() , Channeling.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cartpage:
                        startActivity(new Intent(getApplicationContext() , CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profilepage:
                        startActivity(new Intent(getApplicationContext(), PatientProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void LoadData(String data)
    {
        //create the query for the search
        Query query = DoctorReference.orderByChild("username").startAt(data).endAt(data+"\uf8ff");

        options = new  FirebaseRecyclerOptions.Builder<Doctor>().setQuery(query,Doctor.class).build();
        adapter = new FirebaseRecyclerAdapter<Doctor, DoctorViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Doctor doctor) {

                holder.username.setText("Dr. " + doctor.getUsername());
                holder.speciality.setText(doctor.getSpeciality());
                Picasso.get().load(doctor.getImage()).into(holder.doctor_image);

                holder.doctorView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Homepage.this, ViewDoctor.class);
                        intent.putExtra("DoctorID",doctor.getUserID());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_listview, parent,false);
                return new DoctorViewHolder(view);
            }
        };
        adapter.startListening();
        doctor_recycle_view.setAdapter(adapter);
    }

}


