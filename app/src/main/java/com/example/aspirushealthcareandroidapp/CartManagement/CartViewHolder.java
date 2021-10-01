package com.example.aspirushealthcareandroidapp.CartManagement;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.aspirushealthcareandroidapp.R;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductQuantity, txtProductPrice;
    public ImageView product, minus, add;
    public View btnDelete;
    private ItemClickListner itemClickListner;
    public CheckBox checkProduct;

    public CartViewHolder(View itemView)
    {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        product = itemView.findViewById(R.id.product);
        btnDelete = itemView.findViewById(R.id.delete);
        txtProductQuantity = itemView.findViewById(R.id.integer_number);
        checkProduct = itemView.findViewById(R.id.checkProduct);
    }

    @Override
    public void onClick(View view)
    {
        itemClickListner.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}
