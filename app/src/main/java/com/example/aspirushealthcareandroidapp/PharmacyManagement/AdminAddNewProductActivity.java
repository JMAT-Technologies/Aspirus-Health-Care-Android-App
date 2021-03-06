package com.example.aspirushealthcareandroidapp.PharmacyManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aspirushealthcareandroidapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class AdminAddNewProductActivity extends AppCompatActivity
{
    private TextInputLayout InputProductName,InputProductDescription,InputProductPrice;
    private String Description,ProductName,Price,productRandomKey,downloadImageUrl;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private static final int GalleryPick = 1;
    private Uri ImageURI;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");


        AddNewProductButton = (Button) findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        InputProductName = (TextInputLayout) findViewById(R.id.product_name);
        InputProductDescription = (TextInputLayout) findViewById(R.id.product_description);
        InputProductPrice = (TextInputLayout) findViewById(R.id.product_price);
        loadingBar = new ProgressDialog(this);


        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();

            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();

            }
        });

    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageURI = data.getData();
            InputProductImage.setImageURI(ImageURI);


        }
    }

    private void ValidateProductData()
    {
        Description = InputProductDescription.getEditText().getText().toString();
        Price = InputProductPrice.getEditText().getText().toString();
        ProductName = InputProductName.getEditText().getText().toString();



        if(ImageURI == null)
        {
            Toast.makeText(this, "Product image is mandatory...",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ProductName))
        {
            Toast.makeText(this, "Please write  ProductName...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }

    }

    private void StoreProductInformation()
    {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, Please wait,while we are adding the new product. ");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        //Calendar calendar = Calendar.getInstance();

       // SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
       // saveCurrentDate = currentDate.format(calendar.getTime());

       // SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
       // saveCurrentTime = currentTime.format(calendar.getTime());

       // productRandomKey = saveCurrentTime+saveCurrentDate;

        //unique ID
        productRandomKey = UUID.randomUUID().toString();


        StorageReference filePath = ProductImagesRef.child(productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageURI);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error:" +message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewProductActivity.this, "Product Image Uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });

            }
        });

    }

    private void saveProductInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("ItemKey",productRandomKey);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("price",Price);
        productMap.put("productName",ProductName);

        ProductRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this,
                                    "Product is added successfully...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminAddNewProductActivity.this, AdminPharmacy.class);
                            startActivity(intent);
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this,
                                    "Error:"+ message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void AdminHome(View view) {
        Intent intent = new Intent(this, AdminPharmacy.class);
        startActivity(intent);
    }
}



