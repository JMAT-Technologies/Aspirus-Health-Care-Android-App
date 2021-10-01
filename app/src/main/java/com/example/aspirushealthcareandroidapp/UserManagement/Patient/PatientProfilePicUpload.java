package com.example.aspirushealthcareandroidapp.UserManagement.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.Homepage;
import com.example.aspirushealthcareandroidapp.MainActivity;
import com.example.aspirushealthcareandroidapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PatientProfilePicUpload extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int Gallery_Image = 1;
    String userID;
    String downloadImageUrl;
    Uri ImageURI = Uri.EMPTY;
    ImageView profile_pic_preview;
    Button skip;
    Button btn_lets_go;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_pic_upload);

        //getting the user id from extras passed from sign up activity
        Intent userIDIntent = getIntent();
        userID = userIDIntent.getStringExtra(PatientSignUp.EXTRA_USERID);

        profile_pic_preview = findViewById(R.id.profile_pic_upload);
        skip                = findViewById(R.id.btn_skip);
        btn_lets_go         = findViewById(R.id.btn_lets_go);
        progressBar         = findViewById(R.id.profilepic_progress);
        progressBar.setVisibility(View.GONE);

        //select image function
        profile_pic_preview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showDialog();
            }
        });

        //skip function
        skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        //lets go function
        btn_lets_go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(ImageURI.equals(Uri.EMPTY)){
                    Toast.makeText(PatientProfilePicUpload.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }else {
                    saveData();
                }
            }
        });
    }

    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_dialog_image_upload);

        ImageView open_gallery = dialog.findViewById(R.id.img_gallery);
        ImageView open_camera = dialog.findViewById(R.id.img_camera);
        ImageView close = dialog.findViewById(R.id.img_close);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomDialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        open_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opening the gallery
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"Choose Profile Picture"),Gallery_Image);
                dialog.dismiss();

            }
        });

        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct to camera activity
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profile_pic_preview.setImageBitmap(photo);
        }

        if(requestCode == Gallery_Image && resultCode == RESULT_OK && data != null){
            ImageURI = data.getData();
            profile_pic_preview.setImageURI(ImageURI);
        }
    }

    private void saveData(){

        progressBar.setVisibility(View.VISIBLE);

        StorageReference ProfilePicRef = FirebaseStorage.getInstance().getReference().child("Patient Profile Pictures");
        StorageReference filePath = ProfilePicRef.child(userID + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageURI);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
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

                            database = FirebaseDatabase.getInstance();
                            reference = database.getReference("patients");

                            reference.child(userID).child("image").setValue(downloadImageUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(PatientProfilePicUpload.this, "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();

                                        //redirecting to the profile picture upload
                                        startActivity(new Intent(getApplicationContext(), Homepage.class));
                                        finish();

                                    } else {
                                        Toast.makeText(PatientProfilePicUpload.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(PatientProfilePicUpload.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(PatientProfilePicUpload.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}