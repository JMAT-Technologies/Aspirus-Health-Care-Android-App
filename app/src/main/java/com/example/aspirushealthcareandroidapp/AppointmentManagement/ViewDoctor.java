package com.example.aspirushealthcareandroidapp.AppointmentManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.Homepage;
import com.example.aspirushealthcareandroidapp.PharmacyManagement.Pharmacy;
import com.example.aspirushealthcareandroidapp.R;
import com.example.aspirushealthcareandroidapp.UserManagement.Patient.PatientProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewDoctor extends AppCompatActivity {

    String userID;
    FirebaseAuth firebaseAuth;

    private ImageView imageView;
    TextView speciality;
    EditText username;
    EditText et_appointmentDate;
    EditText et_appointmentTime;
    Button btn_appointmmentChannel;

    DatabaseReference DocRef;
    DatabaseReference appointmentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

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
                        startActivity(new Intent(getApplicationContext() , Channeling.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cartpage:
//                        startActivity(new Intent(getApplicationContext() , CartActivity.class));
//                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profilepage:
                        startActivity(new Intent(getApplicationContext() , PatientProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        et_appointmentDate = findViewById(R.id.et_appointmentDate);
        et_appointmentTime = findViewById(R.id.et_appointmentTime);
        btn_appointmmentChannel = findViewById(R.id.btn_appointmmentChannel);
        imageView=findViewById(R.id.doctor_single_view);
        speciality=findViewById(R.id.speciality_name);
        username = findViewById(R.id.et_doctorName);
        DocRef = FirebaseDatabase.getInstance().getReference().child("doctors");

        et_appointmentDate.setInputType(InputType.TYPE_NULL);
        et_appointmentTime.setInputType(InputType.TYPE_NULL);

        et_appointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(et_appointmentDate);
            }
        });

        et_appointmentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(et_appointmentTime);
            }
        });

        String DoctorKey = getIntent().getStringExtra("DoctorKey");
        DocRef.child(DoctorKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                if (datasnapshot.exists()){
                    String doctorName= datasnapshot.child("username").getValue().toString();
                    String doctorSpeciality= datasnapshot.child("speciality").getValue().toString();
                    String image= datasnapshot.child("image").getValue().toString();

                    Picasso.get().load(image).into(imageView);
                    username.setText(doctorName);
                    speciality.setText(doctorSpeciality);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Add Appointment
        appointmentRef = FirebaseDatabase.getInstance().getReference().child("appointments");
        btn_appointmmentChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String date = et_appointmentDate.getText().toString();
                final String time = et_appointmentTime.getText().toString();
                if(date.length()==0)
                {
                    et_appointmentDate.requestFocus();
                    et_appointmentDate.setError("Please select available date");
                }
                else if(time.length()==0)
                {
                    et_appointmentTime.requestFocus();
                    et_appointmentTime.setError("Please select available time");
                }
                else
                {
                    insertAppointmentData();
                    Intent intent = new Intent(ViewDoctor.this, Appointment.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void insertAppointmentData() {
        String date = et_appointmentDate.getText().toString();
        String time = et_appointmentTime.getText().toString();
        String doctorName = username.getText().toString();

        AppointmentModel appointmentModel = new AppointmentModel(date, time, doctorName);

        appointmentRef.child(userID).push().setValue(appointmentModel);
        Toast.makeText(ViewDoctor.this, "Appointment Added", Toast.LENGTH_SHORT).show();

    }

    //Time picker
    private void showTimeDialog(final EditText et_appointmentTime) {
        final Calendar calendar=Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm a");
                et_appointmentTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(ViewDoctor.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
    }

    //Date picker
    private void showDateDialog(final EditText et_appointmentDate) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
                et_appointmentDate.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(ViewDoctor.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void ChannelingPage(View view) {
        Intent intent = new Intent(this, Channeling.class);
        startActivity(intent);
    }

}