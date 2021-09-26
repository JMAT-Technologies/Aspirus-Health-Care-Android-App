package com.example.aspirushealthcareandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class Appointment extends AppCompatActivity {

    RecyclerView recyclerView;
    AppointmentAdapter appointmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        recyclerView = (RecyclerView)findViewById(R.id.appointmentRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<AppointmentModel> options =
                new FirebaseRecyclerOptions.Builder<AppointmentModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Appointments"), AppointmentModel.class)
                        .build();

        appointmentAdapter = new AppointmentAdapter(options);
        recyclerView.setAdapter(appointmentAdapter);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId((R.id.profilepage));

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
                        startActivity(new Intent(getApplicationContext()
                                ,Channeling.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.pharmacypage:
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        appointmentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        appointmentAdapter.stopListening();
    }
}