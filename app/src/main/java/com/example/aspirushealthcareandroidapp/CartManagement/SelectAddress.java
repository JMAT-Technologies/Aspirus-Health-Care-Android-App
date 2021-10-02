package com.example.aspirushealthcareandroidapp.CartManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aspirushealthcareandroidapp.R;

public class SelectAddress extends AppCompatActivity {
    Button btn_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        btn_address=findViewById(R.id.btn_address);

        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent interestIntent = new Intent(SelectAddress.this, addNewAddress.class);

                startActivity(interestIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}