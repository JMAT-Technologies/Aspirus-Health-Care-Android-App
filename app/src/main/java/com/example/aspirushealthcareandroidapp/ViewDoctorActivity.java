package com.example.aspirushealthcareandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewDoctorActivity extends AppCompatActivity {

    private ImageView imageView;
    TextView speciality;
    EditText name;
    EditText et_appointmentDate;
    EditText et_appointmentTime;
    Button btn_appointmmentChannel;

    DatabaseReference DocRef;
    DatabaseReference appointmentRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);

        et_appointmentDate = findViewById(R.id.et_appointmentDate);
        et_appointmentTime = findViewById(R.id.et_appointmentTime);
        btn_appointmmentChannel = findViewById(R.id.btn_appointmmentChannel);
        imageView=findViewById(R.id.doctor_single_view);
        speciality=findViewById(R.id.speciality_name);
        name = findViewById(R.id.et_doctorName);
        DocRef = FirebaseDatabase.getInstance().getReference().child("TestDoctor");

        String DoctorKey = getIntent().getStringExtra("DoctorKey");

        DocRef.child(DoctorKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                if (datasnapshot.exists()){
                    String doctorName= datasnapshot.child("name").getValue().toString();
                    String doctorSpeciality= datasnapshot.child("speciality").getValue().toString();
                    String image= datasnapshot.child("image").getValue().toString();

                    Picasso.get().load(image).into(imageView);
                    name.setText(doctorName);
                    speciality.setText(doctorSpeciality);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        appointmentRef = FirebaseDatabase.getInstance().getReference().child("Appointments");
        btn_appointmmentChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAppointmentData();
            }
        });

    }

    private void insertAppointmentData(){

        String Date = et_appointmentDate.getText().toString();
        String Time = et_appointmentTime.getText().toString();
        String DoctorName = name.getText().toString();

        AppointmentModel appointmentModel = new AppointmentModel(Date, Time, DoctorName);

        appointmentRef.push().setValue(appointmentModel);
        Toast.makeText(ViewDoctorActivity.this, "Appointment Added", Toast.LENGTH_SHORT).show();
    }

}