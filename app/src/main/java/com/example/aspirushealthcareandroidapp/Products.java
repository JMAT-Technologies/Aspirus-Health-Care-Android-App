package com.example.aspirushealthcareandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Products extends AppCompatActivity {

    //EditText inputSearch;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<ProductModel>options; //pass the model class
    FirebaseRecyclerAdapter<ProductModel,ProductViewHolder>adapter;
    DatabaseReference ProductRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        //inputSearch=findViewById(R.id.inputSeacrh);
        recyclerView=(RecyclerView) findViewById(R.id.recylerView);

        //LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(Products.this, LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(horizontalLayoutManagaer);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
       recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);

        LoadData();
        

    }

    private void LoadData() {
      options = new  FirebaseRecyclerOptions.Builder<ProductModel>().setQuery(ProductRef,ProductModel.class).build();
      adapter = new FirebaseRecyclerAdapter<ProductModel, ProductViewHolder>(options) {
          @Override
          protected void onBindViewHolder(@NonNull ProductViewHolder holder,   int position, @NonNull ProductModel model) {

              holder.productName.setText(model.getProductName());
              holder.price.setText("Rs "+ model.getPrice()+".00");
              Picasso.get().load(model.getImage()).into(holder.imageView);

              holder.view.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent = new Intent(Products.this,ProductOneView.class);
                      intent.putExtra("ItemKey",getRef(position).getKey());

                      startActivity(intent);
                  }
              });




          }

          @NonNull
          @Override
          public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

              View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_listview,parent,false);
              return new ProductViewHolder(view);
          }
      };
      adapter.startListening();
      recyclerView.setAdapter(adapter);
    }
}