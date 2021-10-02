package com.example.aspirushealthcareandroidapp.UserManagement.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.aspirushealthcareandroidapp.AppointmentManagement.Appointment;
import com.example.aspirushealthcareandroidapp.AppointmentManagement.AppointmentModel;
import com.example.aspirushealthcareandroidapp.AppointmentManagement.Channeling;
import com.example.aspirushealthcareandroidapp.AppointmentManagement.ProfileAppointmentAdapter;
import com.example.aspirushealthcareandroidapp.Homepage;
import com.example.aspirushealthcareandroidapp.PharmacyManagement.Pharmacy;
import com.example.aspirushealthcareandroidapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PatientProfile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ImageView profilePic;
    ImageView profile_more_btn;
    TextView tv_username;
    TextView tv_email;
    TextView tv_sugarLevel;
    TextView tv_bloodPressure;
    TextView tv_bloodGroup;
    TextView tv_age;
    TextView tv_height;
    TextView tv_weight;
    TextView tv_bmi;

    String userID;
    FirebaseAuth firebaseAuth;
    DatabaseReference database;

    //Appointment
    RecyclerView recyclerView;
    ProfileAppointmentAdapter profileAppointmentAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), PatientLogin.class));
        }
        //Appointment
        profileAppointmentAdapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        profilePic       = findViewById(R.id.profile_picture);
        profile_more_btn = findViewById(R.id.profile_more_btn);
        tv_username      = findViewById(R.id.username);
        tv_email         = findViewById(R.id.email);
        tv_sugarLevel    = findViewById(R.id.tv_sugarLevel);
        tv_bloodGroup    = findViewById(R.id.tv_bloodGroup);
        tv_bloodPressure = findViewById(R.id.tv_bloodPressure);
        tv_age           = findViewById(R.id.tv_age);
        tv_height        = findViewById(R.id.tv_height);
        tv_weight        = findViewById(R.id.tv_weight);
        tv_bmi           = findViewById(R.id.tv_bmi);

        //Appointment
        recyclerView = findViewById(R.id.appointmentRecyclerViewProfile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //navigation bar
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId((R.id.profilepage));

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
                        startActivity(new Intent(getApplicationContext() , Channeling.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cartpage:
                        startActivity(new Intent(getApplicationContext() , CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profilepage:
                        return true;
                }
                return false;
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference().child("patients");

        database.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //getting data from the database
                    String username = snapshot.child("username").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String age = snapshot.child("age").getValue().toString();
                    String bloodGroup = snapshot.child("bloodGroup").getValue().toString();
                    String bloodPressure = snapshot.child("bloodPressure").getValue().toString();
                    String sugarLevel = snapshot.child("sugarLevel").getValue().toString();
                    String height = snapshot.child("height").getValue().toString();
                    String weight = snapshot.child("weight").getValue().toString();
                    String bmi = snapshot.child("bmi").getValue().toString();
                    String image = snapshot.child("image").getValue().toString();

                    //setting the values to views
                    Picasso.get().load(image).into(profilePic);
                    tv_username.setText(username);
                    tv_email.setText(email);
                    tv_age.setText(age);

                    if(!bloodGroup.equals("null")){
                        tv_bloodGroup.setText(bloodGroup);
                    }else{
                        updateBloodGroup();
                    }

                    if(!bloodPressure.equals("null")){
                        tv_bloodPressure.setText(bloodPressure);
                    }else{
                        updateBloodPressure();
                    }

                    if(Double.parseDouble(sugarLevel) > 0){
                        tv_sugarLevel.setText(sugarLevel);
                    }else{
                        updateSugarLevel();
                    }

                    if(Double.parseDouble(weight) <= 0 | Double.parseDouble(height) <= 0 | Double.parseDouble(bmi) <= 0){
                        updateBmi();
                    }else{
                        tv_height.setText(height + " m");
                        tv_weight.setText(weight + " kg");
                        tv_bmi.setText(bmi);

                        if(Double.parseDouble(bmi) >= 24.9){
                            tv_bmi.setTextColor(getResources().getColor(R.color.red));
                        }else if(Double.parseDouble(bmi) >= 18.5){
                            tv_bmi.setTextColor(getResources().getColor(R.color.green));
                        } else if(Double.parseDouble(bmi) <= 18.5 && Double.parseDouble(bmi) > 0 ){
                            tv_bmi.setTextColor(getResources().getColor(R.color.yellow));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //profile more function
        profile_more_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                PopupMenu popupMenu = new PopupMenu(PatientProfile.this, view);
                popupMenu.setOnMenuItemClickListener(PatientProfile.this);
                popupMenu.inflate(R.menu.profile_menu);
                popupMenu.show();
            }
        });
        loadAppointments();
    }

    //profile menu
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.edit_profile:
                //redirecting to the profile picture upload
                startActivity(new Intent(getApplicationContext(), UpdatePatientProfile.class));
                return true;

            case R.id.btn_logout:
                //redirecting to the profile picture upload
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), PatientLogin.class));
                return true;

            default:
                return false;
        }
    }

    private void updateBloodGroup(){}
    private void updateBloodPressure(){}
    private void updateSugarLevel(){}
    private void updateBmi(){}

    //Appointment
    private void loadAppointments(){
        DatabaseReference AppointmentRef = FirebaseDatabase.getInstance().getReference().child("appointments");

        FirebaseRecyclerOptions<AppointmentModel> options = new FirebaseRecyclerOptions.Builder<AppointmentModel>().setQuery(AppointmentRef.child(userID).orderByChild("date").limitToFirst(5), AppointmentModel.class).build();
        profileAppointmentAdapter = new ProfileAppointmentAdapter(options);
        recyclerView.setAdapter(profileAppointmentAdapter);
    }

    //Appointment
    @Override
    protected void onStop() {
        super.onStop();
        profileAppointmentAdapter.stopListening();
    }

    public void Appointment(View view) {
        Intent intent = new Intent(this, Appointment.class);
        startActivity(intent);
    }
}