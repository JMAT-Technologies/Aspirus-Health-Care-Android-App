package com.example.aspirushealthcareandroidapp.PharmacyManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aspirushealthcareandroidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductOneView extends AppCompatActivity {

    private Button addToCartBtn;// cart
    private Button buybtn;
    private ImageView imageView,cartimage,buyimage;
    TextView productName,price,description,cartproductname,cartprice,cartqty,buycartproductname,buyprice;
    DatabaseReference CartRef;
    DatabaseReference BuyRef;
    DatabaseReference ProductRef;
    private String ItemKey = " ";
    String  UserId = "A1";
    ImageView plus,minus;
    private int mCounter = 1 ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_one_view);


        imageView=findViewById(R.id.image_single_viewActivity);
        productName = findViewById(R.id.product_nameActivity);
        price = findViewById(R.id.product_priceActivity);
        description = findViewById(R.id.product_descriptionActivity);



        //Add To Cart
        ItemKey = getIntent().getStringExtra("ItemKey");
        addToCartBtn=findViewById(R.id.addtocartbtn); // cart
        cartproductname=findViewById(R.id.product_nameActivity);
        cartprice=findViewById(R.id.product_priceActivity);
        cartimage=findViewById(R.id.image_single_viewActivity);

        ///buy button

        ItemKey = getIntent().getStringExtra("ItemKey");
        buybtn=findViewById(R.id.btn_buy); // cart
        buycartproductname=findViewById(R.id.product_nameActivity);
        buyprice=findViewById(R.id.product_priceActivity);
        buyimage=findViewById(R.id.image_single_viewActivity);

///////////////////////////////////////////////////////////////////////////
        //calculate product quantity
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        cartqty = findViewById(R.id.cartqty);



        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCounter < 10) {
                    mCounter++;
                    cartqty.setText(Integer.toString(mCounter));

                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCounter > 1) {
                    mCounter--;
                    cartqty.setText(Integer.toString(mCounter));


                }
            }
        });




/////////////////////////////ADD TO CART //////////////////////////////////////////////////////
        getProductDetails(ItemKey);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
            }

        });

        //buy
        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToBuy();
            }

        });

 //////////////////// view one product/////////////////////////
        DatabaseReference CartRef;

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
                    price.setText("Rs."+Price+".00");
                    description.setText(Description);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


     
    }

    //buy addToBuy method

    private void addToBuy() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForeDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForeDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForeDate.getTime());

        final DatabaseReference CartListRef = FirebaseDatabase.getInstance().getReference().child("Buy");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("ItemKey", ItemKey);
        cartMap.put("productName", productName.getText().toString());
        cartMap.put("price", price.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", cartqty.getText().toString());
        //cartMap.put("image",imageView);




        CartListRef.child(UserId)
                .child(ItemKey)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ProductOneView.this,"Ready to Buy", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProductOneView.this, Pharmacy.class);
                            startActivity(intent);
                        }
                    }
                });
    }
    // Add To Cart
    private void addingToCartList() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForeDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForeDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForeDate.getTime());

        final DatabaseReference CartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("ItemKey", ItemKey);
        cartMap.put("productName", productName.getText().toString());
       // price=Integer.valueOf(dprice);
        cartMap.put("price", price.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", cartqty.getText().toString());
        //cartMap.put("image",imageView);//sending image to the cart


        CartListRef.child(UserId)
                .child(ItemKey)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(ProductOneView.this,"Added to cart List", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(ProductOneView.this, Pharmacy.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });

                    }






    private void getProductDetails(String itemKey) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        productsRef.child(ItemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ProductModel productsModel = dataSnapshot.getValue(ProductModel.class);

                    productName.setText(productsModel.getProductName());
                    price.setText(productsModel.getPrice());
                    Picasso.get().load(productsModel.getImage()).into(imageView);

                }

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void userAllProducts(View view) {
        Intent intent = new Intent(this, Pharmacy.class);
        startActivity(intent);
    }
}