package com.example.aspirushealthcareandroidapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class AdminPharmacyAdapter extends FirebaseRecyclerAdapter<AdminPharmacyModel, AdminPharmacyAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    Context context;
    public AdminPharmacyAdapter(@NonNull FirebaseRecyclerOptions<AdminPharmacyModel> options) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull AdminPharmacyModel model) {
        holder.productName.setText(model.getProductName());
        holder.price.setText("Rs "+model.getPrice()+".00");
        //holder.description.setText(model.getDescription());

        Glide.with(holder.image.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.medi)
                .error(R.drawable.select_product_image)
                .into(holder.image);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(holder.image.getContext())
                            .setContentHolder(new ViewHolder(R.layout.admin_product_update_popup))
                            .setExpanded(true,2022)
                            .create();
                   // dialogPlus.show();
                view = dialogPlus.getHolderView();

                TextInputLayout productName = view.findViewById(R.id.productName);
                TextInputLayout price = view.findViewById(R.id.product_price);
                TextInputLayout description = view.findViewById(R.id.product_description);
               // EditText image = view.findViewById(R.id.ImageUrl);

                Button btnUpdate =view.findViewById(R.id.btnUpdate);

                description.getEditText().setText(model.getDescription());
                productName.getEditText().setText (model.getProductName());
                price.getEditText().setText(model.getPrice());
                //image.setText(model.getImage());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap< >();
                        map.put("productName",productName.getEditText().getText().toString());
                        map.put("price",price.getEditText().getText().toString());
                        map.put("description",description.getEditText().getText().toString());
                       // map.put("image",image.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Products")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.productName.getContext(),
                                                "Data Updated Successfully.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();//popup down
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {
                                        Toast.makeText(holder.productName.
                                                getContext(),"Data Update Error.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();//popup down
                                    }
                                });
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder buildDialog = new AlertDialog.Builder(holder.productName.getContext());
                buildDialog.setTitle("Are you Sure ?");
                buildDialog.setMessage("Deleted Data can't be Undo");


                buildDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Products")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                buildDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.productName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                buildDialog.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_pharmacy_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView productName,price,description;
        Button btnEdit,btnDelete;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);


            image= (ImageView) itemView.findViewById(R.id.product_image);
            productName=(TextView) itemView.findViewById(R.id.product_name);
            price=(TextView) itemView.findViewById(R.id.product_price);
            description=(TextView) itemView.findViewById(R.id.product_description);

            btnEdit = (Button)itemView.findViewById(R.id.btn_edit);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete);


        }
    }
}
