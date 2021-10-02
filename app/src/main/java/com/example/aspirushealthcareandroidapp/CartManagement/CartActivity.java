package com.example.aspirushealthcareandroidapp.CartManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.AppointmentManagement.Channeling;
import com.example.aspirushealthcareandroidapp.Homepage;
import com.example.aspirushealthcareandroidapp.PharmacyManagement.Pharmacy;
import com.example.aspirushealthcareandroidapp.R;
import com.example.aspirushealthcareandroidapp.UserManagement.Patient.PatientProfile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button btn_checkout;
    private Map<String, Double> itemKeys = new HashMap<>();
    String userID;
    FirebaseAuth firebaseAuth;
    public static final String EXTRA_ITEM_MAP  = "com.example.aspirushealthcareandroidapp.CartManagement.ITEM_MAP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        btn_checkout = findViewById(R.id.btn_checkout);

        loadCart();

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("HashMapTest", String.valueOf(itemKeys.get("0c109b13-c473-4542-bd73-3ca66202f1bc")));
                Log.v("HashMapTest2", String.valueOf(itemKeys.get("6c8b8129-c8c4-4d8d-9875-a2a674df3822")));
                Intent Checkout = new Intent(CartActivity.this, orderConfirmation.class);
                Checkout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Checkout.putExtra(EXTRA_ITEM_MAP, (Serializable) itemKeys);
                startActivity(Checkout);
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
                       startActivity(new Intent(getApplicationContext() , Pharmacy.class));
                        overridePendingTransition(0,0);
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

    private void loadCart(){
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child(userID),Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Cart model)
            {
                DatabaseReference productDBRef = FirebaseDatabase.getInstance().getReference().child("Products");

                productDBRef.child(model.getItemKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            //getting data from the database
                            String image = snapshot.child("image").getValue().toString();
                            String price = snapshot.child("price").getValue().toString();
                            String Name = snapshot.child("productName").getValue().toString();
                            model.setProductName(Name);
                            model.setPrice(Double.valueOf(price));
                            model.setImage(image);

                            holder.txtProductQuantity.setText(String.valueOf(model.getQuantity()));
                            holder.txtProductPrice.setText("LKR " +model.getPrice());
                            holder.txtProductName.setText(model.getProductName());
                            Picasso.get().load(model.getImage()).into(holder.product);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                delete
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        cartListRef.child(userID).child(model.getProductName()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>(){
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

                holder.checkProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.checkProduct.isChecked()){
                            double totalPrice = model.getQuantity() * model.getPrice();
                            itemKeys.put(model.getItemKey(),totalPrice);
                            Toast.makeText(CartActivity.this, String.valueOf(totalPrice), Toast.LENGTH_SHORT).show();
                        }else if (!holder.checkProduct.isChecked()){
                            itemKeys.remove(model.getItemKey());
                            Toast.makeText(CartActivity.this, "fuck", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
