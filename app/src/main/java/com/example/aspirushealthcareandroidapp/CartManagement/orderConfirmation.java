package com.example.aspirushealthcareandroidapp.CartManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aspirushealthcareandroidapp.Homepage;
import com.example.aspirushealthcareandroidapp.PharmacyManagement.ProductOneView;
import com.example.aspirushealthcareandroidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class orderConfirmation extends AppCompatActivity {

    ImageView order_product, changeAddress;
    TextView oder_product_name,oder_product_price, order_quantity,totalPrice;
    Button btn_order;
    String userID;
    String ItemID;
    String quantity;
    String totalDB;
    FirebaseAuth firebaseAuth;
    DatabaseReference confirmListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        Intent buyIntent = getIntent();
        ItemID = buyIntent.getStringExtra(ProductOneView.EXTRA_ITEM_ID);
        quantity = buyIntent.getStringExtra(ProductOneView.EXTRA_ITEM_QTY);

        oder_product_name = findViewById(R.id.order_product_name);
        oder_product_price = findViewById(R.id.order_product_price);
        order_quantity = findViewById(R.id.order_quantity);
        order_product = findViewById(R.id.oder_product);
        totalPrice = findViewById(R.id.totalPrice);
        btn_order = findViewById(R.id.btn_order);
        changeAddress = findViewById(R.id.changeAddress);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        //checkout btn
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent confirm = new Intent(orderConfirmation.this, Homepage.class);
                startActivity(confirm);
                addingToBuyTable();
            }
        });

        //add address
        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent address = new Intent(orderConfirmation.this, SelectAddress.class);
                startActivity(address);
            }
        });


        loadCart();
    }

    private void addingToBuyTable() {
        final DatabaseReference confirmListRef = FirebaseDatabase.getInstance().getReference().child("orders");
        final HashMap<String, Object> confirmMap = new HashMap<>();
        confirmMap.put("ItemKey", ItemID);
        confirmMap.put("quantity",quantity);
        confirmMap.put("Total",totalDB);

        confirmListRef.child(userID).child(ItemID).updateChildren(confirmMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(orderConfirmation.this,"Order Confirm", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadCart() {

        DatabaseReference productDBRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productDBRef.child(ItemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //getting data from the database
                    String image = snapshot.child("image").getValue().toString();
                    String price = snapshot.child("price").getValue().toString();
                    String Name = snapshot.child("productName").getValue().toString();
                    double total = Integer.valueOf(quantity)*Double.valueOf(price);
                    totalDB = String.valueOf(total);

                    oder_product_name.setText(Name);
                    order_quantity.setText(String.valueOf(quantity));
                    oder_product_price.setText("LKR " + price);
                    Picasso.get().load(image).into(order_product);
                    totalPrice.setText(String.valueOf(total+"0"));


                }else {
                    Toast.makeText(orderConfirmation.this,"This item no longer available", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(orderConfirmation.this,"Failed to fetch product", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
