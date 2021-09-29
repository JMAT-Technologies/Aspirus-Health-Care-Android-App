package com.example.aspirushealthcareandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.UserManagement.PatientLogin;
import com.example.aspirushealthcareandroidapp.UserManagement.PatientProfile;
import com.example.aspirushealthcareandroidapp.UserManagement.UpdatePatientProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //navigation bar
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId((R.id.homepage));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homepage:
                        return true;
//                    case R.id.pharmacypage:
//                        startActivity(new Intent(getApplicationContext() , Pharmacy.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.channelingpage:
                        startActivity(new Intent(getApplicationContext() , Channeling.class));
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.cartpage:
//                        startActivity(new Intent(getApplicationContext() , CartActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.profilepage:
                        startActivity(new Intent(getApplicationContext(), PatientProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}


