package com.example.aspirushealthcareandroidapp.CartManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.R;

public class addNewAddress extends AppCompatActivity {
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);
        save = findViewById(R.id.save);

        //save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newAddress = new Intent(addNewAddress.this, SelectAddress.class);
                Toast.makeText(addNewAddress.this,"Add new Address", Toast.LENGTH_LONG).show();
                startActivity(newAddress);
            }
        });
    }
}