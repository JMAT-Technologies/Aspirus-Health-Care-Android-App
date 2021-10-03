package com.example.aspirushealthcareandroidapp.CartManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.R;

public class SelectAddress extends AppCompatActivity {
    Button btn_address;
    RadioButton radioButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        btn_address=findViewById(R.id.btn_address);
        radioButton1 = findViewById(R.id.radioButton1);

        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent interestIntent = new Intent(SelectAddress.this, addNewAddress.class);
                startActivity(interestIntent);
            }
        });

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAddress = new Intent(SelectAddress.this, orderConfirmation.class);
                Toast.makeText(SelectAddress.this,"Address selected", Toast.LENGTH_LONG).show();
                startActivity(addAddress);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}