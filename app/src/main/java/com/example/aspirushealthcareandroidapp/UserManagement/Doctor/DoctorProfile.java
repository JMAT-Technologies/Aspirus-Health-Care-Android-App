package com.example.aspirushealthcareandroidapp.UserManagement.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.aspirushealthcareandroidapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorProfile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ImageView doc_profile_pic;
    ImageView doc_profile_more_btn;
    TextView doc_username;
    TextView doc_email;
    TextView tv_doc_specialization;
    TextView doc_total;
    TextView doc_monthly;

    String userID;
    FirebaseAuth firebaseAuth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        doc_profile_pic       = findViewById(R.id.doc_profile_pic);
        doc_profile_more_btn  = findViewById(R.id.doc_profile_more_btn);
        doc_username          = findViewById(R.id.doc_username);
        doc_email             = findViewById(R.id.doc_email);
        tv_doc_specialization = findViewById(R.id.tv_doc_specialization);
        doc_total             = findViewById(R.id.doc_total);
        doc_monthly           = findViewById(R.id.doc_monthly);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference().child("doctors");

        database.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //getting data from the database
                    String username = snapshot.child("username").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String age = snapshot.child("age").getValue().toString();
                    String specialization = snapshot.child("speciality").getValue().toString();
//                    String image = snapshot.child("image").getValue().toString();

                    //setting the values to views
//                    Picasso.get().load(image).into(doc_profile_pic);
                    doc_username.setText(username);
                    doc_email.setText(email);
                    tv_doc_specialization.setText(specialization);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //profile more function
        doc_profile_more_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                PopupMenu popupMenu = new PopupMenu(DoctorProfile.this, view);
                popupMenu.setOnMenuItemClickListener(DoctorProfile.this);
                popupMenu.inflate(R.menu.profile_menu);
                popupMenu.show();
            }
        });
    }

    //profile menu
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.edit_profile:
                //redirecting to the edit profile
//                startActivity(new Intent(getApplicationContext(), UpdateDoctorProfile.class));
                return true;

            case R.id.btn_logout:
                //redirecting to the login page
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), DoctorLogin.class));
                return true;

            default:
                return false;
        }
    }
}