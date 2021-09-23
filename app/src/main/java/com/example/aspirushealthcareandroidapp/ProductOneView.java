package com.example.aspirushealthcareandroidapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductOneView extends AppCompatActivity {

    private ImageView imageView;
    TextView productName,price,description;
    //Button btnDelete;

    DatabaseReference ProductRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_one_view);

        imageView=findViewById(R.id.image_single_viewActivity);
        productName = findViewById(R.id.product_nameActivity);
        price = findViewById(R.id.product_priceActivity);
        description = findViewById(R.id.product_descriptionActivity);
        //btnDelete=findViewById(R.id.btnDelete);
        ProductRef= FirebaseDatabase.getInstance().getReference().child("Products");

        String ItemKey = getIntent().getStringExtra("ItemKey");

        ProductRef. child(ItemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String ProductName = dataSnapshot.child("productName").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String Price = dataSnapshot.child("price").getValue().toString();
                    String Description = dataSnapshot.child("description").getValue().toString();

                    Picasso.get().load(image).into(imageView);
                    productName.setText(ProductName);
                    price.setText(Price);
                    description.setText(Description);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}