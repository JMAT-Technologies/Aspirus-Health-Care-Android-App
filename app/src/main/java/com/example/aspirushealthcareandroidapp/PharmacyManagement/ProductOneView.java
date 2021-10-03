package com.example.aspirushealthcareandroidapp.PharmacyManagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aspirushealthcareandroidapp.CartManagement.CartActivity;
import com.example.aspirushealthcareandroidapp.CartManagement.orderConfirmation;
import com.example.aspirushealthcareandroidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProductOneView extends AppCompatActivity {

    private Button addToCartBtn;// cart
    private Button buybtn;
    private ImageView imageView,cartimage,buyimage;
    TextView productName,price,description,cartproductname,cartprice,cartqty,buycartproductname,buyprice;
    DatabaseReference ProductRef;
    private StorageReference ProductImagesRef;
    private String ItemKey = " ";
    String userID;
    FirebaseAuth firebaseAuth;
    ImageView plus,minus;
    private int mCounter = 1 ;

    public static final String EXTRA_ITEM_ID  = "com.example.aspirushealthcareandroidapp.PharmacyManagement.ITEM_ID";
    public static final String EXTRA_ITEM_QTY  = "com.example.aspirushealthcareandroidapp.PharmacyManagement.QUANTITY";


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_one_view);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        ItemKey = getIntent().getStringExtra("ItemKey");
        imageView=findViewById(R.id.image_single_viewActivity);
        productName = findViewById(R.id.product_nameActivity);
        price = findViewById(R.id.product_amount);
        description = findViewById(R.id.product_descriptionActivity);
        addToCartBtn=findViewById(R.id.addtocartbtn);
        buybtn=findViewById(R.id.btn_buy);

        //calculate product quantity
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        cartqty = findViewById(R.id.cartqty);
        cartqty.setText(Integer.toString(mCounter));

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

              //ADD TO CART
        getProductDetails(ItemKey);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
            }
        });

           // view one product
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
                    price.setText(Price+".00");
                    description.setText(Description);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //buy
        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buyIntent = new Intent(getApplicationContext(),orderConfirmation.class);
                buyIntent.putExtra(EXTRA_ITEM_ID,ItemKey);
                buyIntent.putExtra(EXTRA_ITEM_QTY,String.valueOf(mCounter));
                startActivity(buyIntent);
            }
        });
    }

    // Add To Cart
    private void addingToCartList() {


        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Cart Images");
        final DatabaseReference CartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("ItemKey", ItemKey);
        cartMap.put("quantity",mCounter);
        //cartMap.put("image",image.getUrls().toString());
        //cartMap.put("image",imageView);//sending image to the cart

        CartListRef.child(userID).child(ItemKey).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(ProductOneView.this,"Added to cart List", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ProductOneView.this, CartActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void getProductDetails(String itemKey) {

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        productsRef.child(ItemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ProductModel productsModel = dataSnapshot.getValue(ProductModel.class);

                    productName.setText(productsModel.getProductName());
                    price.setText(productsModel.getPrice());

                   // image.getUrls();

                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    public void userAllProducts(View view) {
        Intent intent = new Intent(this, Pharmacy.class);
        startActivity(intent);
    }
}