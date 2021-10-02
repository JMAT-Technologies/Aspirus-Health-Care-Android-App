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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aspirushealthcareandroidapp.AppointmentManagement.Channeling;
import com.example.aspirushealthcareandroidapp.AppointmentManagement.ViewDoctor;
import com.example.aspirushealthcareandroidapp.CartManagement.CartActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

public class Homepage extends AppCompatActivity {

    EditText doctor_search;
    RecyclerView doctor_recycle_view;
    FirebaseRecyclerOptions<Doctor> options; //pass the model class
    FirebaseRecyclerAdapter<Doctor, DoctorViewHolder> adapter;
    DatabaseReference DoctorReference;
    TextView tv_deaths, tv_totalDeaths, tv_cases, tv_totalCases, tv_inHospitals, tv_recovered;

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

        //top docs
        DoctorReference = FirebaseDatabase.getInstance().getReference().child("doctors");
        doctor_search = findViewById(R.id.doctor_search);
        doctor_recycle_view = (RecyclerView) findViewById(R.id.doctor_recycle_view);

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(Homepage.this, LinearLayoutManager.HORIZONTAL, false);
        doctor_recycle_view.setLayoutManager(horizontalLayoutManagaer);

//        this code is for vertical recycle view
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

        //covid dashboard
        tv_deaths = findViewById(R.id.tv_deaths);
        tv_totalDeaths = findViewById(R.id.tv_total_deaths);
        tv_cases = findViewById(R.id.tv_cases);
        tv_totalCases = findViewById(R.id.tv_total_cases);
        tv_inHospitals = findViewById(R.id.tv_hospitals);
        tv_recovered = findViewById(R.id.tv_recovered);

        loadCovid();

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
                        intent.putExtra("DoctorKey", getRef(position).getKey());
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


    public void PharmacyGif(View view) {
        startActivity(new Intent(getApplicationContext() , Pharmacy.class));
    }

    private void loadCovid(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String api = "https://www.hpb.health.gov.lk/api/get-current-statistical";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, api, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");

                    String deaths = data.getString("local_new_deaths");
                    String totalDeaths = data.getString("local_deaths");
                    String cases = data.getString("local_new_cases");
                    String totalCases = data.getString("local_total_cases");
                    String inHospitals = data.getString("local_total_number_of_individuals_in_hospitals");
                    String recovered = data.getString("local_recovered");

                    tv_deaths.setText(deaths);
                    tv_totalDeaths.setText(totalDeaths);
                    tv_cases.setText(cases);
                    tv_totalCases.setText(totalCases);
                    tv_inHospitals.setText(inHospitals);
                    tv_recovered.setText(recovered);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Homepage.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}


