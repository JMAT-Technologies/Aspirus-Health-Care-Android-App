package com.example.aspirushealthcareandroidapp.CartManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.AppointmentManagement.Channeling;
import com.example.aspirushealthcareandroidapp.Homepage;
import com.example.aspirushealthcareandroidapp.R;
import com.example.aspirushealthcareandroidapp.UserManagement.Patient.PatientProfile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView totalP;
    private Button btn_checkout;
    String userId="A1";
    double total = 0;

    private int overTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        totalP = findViewById(R.id.totalP);
        btn_checkout = findViewById(R.id.btn_checkout);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //              checkout button
                Intent checkout = new Intent(CartActivity.this, orderConfirmation.class);
                checkout.putExtra("totalP", totalP.getText().toString());
                startActivity(checkout);
            }
        });
        //Navigation
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId((R.id.cartpage));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homepage:
                        startActivity(new Intent(getApplicationContext(), Homepage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pharmacypage:
//                        startActivity(new Intent(getApplicationContext() , Pharmacy.class));
//                        overridePendingTransition(0,0);
                        return true;
                    case R.id.channelingpage:
                        startActivity(new Intent(getApplicationContext() , Channeling.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cartpage:
                        return true;
                    case R.id.profilepage:
                        startActivity(new Intent(getApplicationContext() , PatientProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference confirmListRef = FirebaseDatabase.getInstance().getReference().child("confirmList");

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef
                                .child(userId),Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Cart model)
            {
                holder.txtProductQuantity.setText(String.valueOf(model.getQuantity()));
                holder.txtProductPrice.setText("LKR " + model.getPrice());
                holder.txtProductName.setText(model.getProductName());
                Picasso.get().load(model.getImage()).into(holder.product);

//                delete
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        cartListRef.child(userId).child(model.getItemKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>(){
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful()) {
                                    Toast.makeText(CartActivity.this, "Item Removed successfully", Toast.LENGTH_SHORT).show();
                                }else{
                                Toast.makeText(CartActivity.this, "Item Removed Unsuccessfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

//                holder.add.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int mCount=model.getQuantity();
//                        double productPrice = model.getPrice();
//                        double productTotal;
//                        if (mCount < 10 ){
//                            mCount ++;
//                            productTotal = productPrice*mCount;
//                            holder.txtProductQuantity.setText(String.valueOf(mCount));
//                            mCount=Integer.valueOf(mCount);
//                            cartListRef.child(userId).child(model.getItemKey()).child("price").setValue(productTotal);
//                            cartListRef.child(userId).child(model.getItemKey()).child("quantity").setValue(mCount);
//                        }
//                    }
//                });
//
//                holder.minus.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int mCount=model.getQuantity();
//                        double productPrice = model.getPrice();
//                        double productTotal;
//                        if (mCount <= 10 & mCount > 1){
//                            mCount--;
//                            productTotal = productPrice*mCount;
//                            holder.txtProductQuantity.setText(String.valueOf(mCount));
//                            mCount=Integer.valueOf(mCount);
//                            cartListRef.child(userId).child(model.getItemKey()).child("price").setValue(productTotal);
//                            cartListRef.child(userId).child(model.getItemKey()).child("quantity").setValue(mCount);
//                        }
//                    }
//                });

                if(holder.checkProduct.isChecked()){
                    double productPrice = model.getPrice();
                    String pName = model.getProductName();
                    String itemID = model.getItemKey();
                    String image = model.getImage();
                    int qty = model.getQuantity();
                    total= (double) (total+productPrice);
                    totalP.setText(String.valueOf(total));
                    confirmListRef.child(userId).child("product").child("ItemKey").setValue(itemID);
                    confirmListRef.child(userId).child("product").child("pName").setValue(pName);
                    confirmListRef.child(userId).child("product").child("image").setValue(image);
                    confirmListRef.child(userId).child("product").child("quantity").setValue(qty);
                    confirmListRef.child(userId).child("product").child("total").setValue(total);
                    Toast.makeText(CartActivity.this, "Item Select", Toast.LENGTH_SHORT).show();

                }
//                else if(!holder.checkProduct.isChecked()){
//                    total= 0;
//                    totalP.setText(String.valueOf(total));
//                    confirmListRef.child(userId).child("product").removeValue();
////                    cartListRef.child(userId).child("Total").setValue(total);
//                    Toast.makeText(CartActivity.this, "Item Uncheck", Toast.LENGTH_SHORT).show();
//                }

            }
            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
