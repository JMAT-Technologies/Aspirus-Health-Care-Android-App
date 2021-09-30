package com.example.aspirushealthcareandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aspirushealthcareandroidapp.UserManagement.Patient.PatientLogin;
import com.google.firebase.auth.FirebaseAuth;

import com.example.aspirushealthcareandroidapp.PharmacyManagement.Pharmacy;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private  Timer splashTimer;
    private static final long DELAY=2600;
    private boolean schedule = false;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        splashTimer = new Timer();

        splashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(context, Homepage.class);
                startActivity(intent);
            }
        },DELAY);
        schedule = true ;

    }
}