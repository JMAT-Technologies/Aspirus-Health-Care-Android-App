package com.example.aspirushealthcareandroidapp.PharmacyManagement;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aspirushealthcareandroidapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView; //initialize image view
    TextView productName,price;

    View view;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.image_single_view);
        productName =  itemView.findViewById(R.id.product_name);
        price =  itemView.findViewById(R.id.product_price);
        view=itemView;

    }
}
