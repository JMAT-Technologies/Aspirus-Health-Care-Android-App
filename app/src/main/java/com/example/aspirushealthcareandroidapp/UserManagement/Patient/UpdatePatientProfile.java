package com.example.aspirushealthcareandroidapp.UserManagement.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspirushealthcareandroidapp.Homepage;
import com.example.aspirushealthcareandroidapp.R;
import com.example.aspirushealthcareandroidapp.UserManagement.UserValidations;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdatePatientProfile extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int Gallery_Image = 1;

    ImageView profilePic;
    Uri ImageURI = Uri.EMPTY;
    String downloadImageUrl = null;
    TextInputLayout et_username;
    TextInputLayout et_phone;
    TextInputLayout et_bloodGroup;
    TextInputLayout et_bloodPressure;
    TextInputLayout et_sugarLevel;
    TextInputLayout et_height;
    TextInputLayout et_weight;
    TextView btn_cancel;
    TextView btn_done;
    Button btn_delete;
    Button btn_resetPassword;

    FirebaseUser user;
    String userID;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient_profile);

        profilePic         = findViewById(R.id.update_profile_pic);
        et_username        = findViewById(R.id.update_username);
        et_phone           = findViewById(R.id.update_phone);
        et_bloodGroup      = findViewById(R.id.update_bloodGroup);
        et_bloodPressure   = findViewById(R.id.update_bloodPressure);
        et_sugarLevel      = findViewById(R.id.update_sugarLevel);
        et_weight          = findViewById(R.id.update_weight);
        et_height          = findViewById(R.id.update_height);
        btn_cancel         = findViewById(R.id.btn_cancel_update);
        btn_done           = findViewById(R.id.btn_done_update);
        btn_delete         = findViewById(R.id.btn_deleteProfile);
        btn_resetPassword  = findViewById(R.id.btn_resetPassword);


        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        database = FirebaseDatabase.getInstance().getReference().child("patients");

        //getting data from the database
        database.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String username = snapshot.child("username").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    String bloodGroup = snapshot.child("bloodGroup").getValue().toString();
                    String bloodPressure = snapshot.child("bloodPressure").getValue().toString();
                    String sugarLevel = snapshot.child("sugarLevel").getValue().toString();
                    String height = snapshot.child("height").getValue().toString();
                    String weight = snapshot.child("weight").getValue().toString();
                    String image = snapshot.child("image").getValue().toString();

                    //setting the values to views
                    Picasso.get().load(image).into(profilePic);
                    et_username.getEditText().setText(username);
                    et_phone.getEditText().setText(phone);
                    et_bloodGroup.getEditText().setText(bloodGroup);
                    et_bloodPressure.getEditText().setText(bloodPressure);
                    et_sugarLevel.getEditText().setText(sugarLevel);
                    et_height.getEditText().setText(height);
                    et_weight.getEditText().setText(weight);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //update profile function
        btn_done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                updateProfile();
            }
        });

        //cancel update function
        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), PatientProfile.class));
            }
        });

        //delete profile function
        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                deleteProfile();
            }
        });

        //change profile function
        profilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showDialog();
            }
        });
    }

    //profile picture update popup
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
            profilePic.setImageBitmap(photo);
        }

        if(requestCode == Gallery_Image && resultCode == RESULT_OK && data != null){
            ImageURI = data.getData();
            profilePic.setImageURI(ImageURI);
        }
    }

    private void saveData(){

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
                        } else {
                            Toast.makeText(UpdatePatientProfile.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(UpdatePatientProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateProfile(){
        String username = et_username.getEditText().getText().toString();
        String phone = et_phone.getEditText().getText().toString();
        String bloodGroup = et_bloodGroup.getEditText().getText().toString();
        String bloodPressure = et_bloodPressure.getEditText().getText().toString();
        String sugarLevel = et_sugarLevel.getEditText().getText().toString();
        String height = et_height.getEditText().getText().toString();
        String weight = et_weight.getEditText().getText().toString();

        saveData();

        if(UserValidations.usernameNull(et_username)){
            return;
        }

        if(!UserValidations.phoneValidate(et_phone)) {
            return;
        }

        if(!UserValidations.weightValidation(et_weight)){
            return;
        }

        if(!UserValidations.heightValidation(et_height)){
            return;
        }

        if(!UserValidations.bloodGroupValidation(et_bloodGroup)){
            return;
        }

        if(!UserValidations.bloodPressureValidation(et_bloodPressure)){
            return;
        }

        if(!UserValidations.sugarLevelValidation(et_sugarLevel)){
            return;
        }

        double bmi = UserValidations.calculateBmi(height, weight);


        Map<String, Object> updatedPatient = new HashMap< >();
        updatedPatient.put("username",username);
        updatedPatient.put("phone",phone);
        updatedPatient.put("bloodGroup",bloodGroup);
        updatedPatient.put("bloodPressure",bloodPressure);
        updatedPatient.put("sugarLevel",sugarLevel);
        updatedPatient.put("height",height);
        updatedPatient.put("weight",weight);
        updatedPatient.put("bmi",bmi);
        if(downloadImageUrl != null){
            updatedPatient.put("image",downloadImageUrl);
        }

        database.child(userID).updateChildren(updatedPatient).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Profile Updated Successfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PatientProfile.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void deleteProfile(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(UpdatePatientProfile.this);
        dialog.setTitle("Confirm Your Password");
        // Set up the input
        final EditText input = new EditText(UpdatePatientProfile.this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setHint("Enter Your Password");
        dialog.setView(input);

        dialog.setMessage("This action can't be undone");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String confirmPW = input.getText().toString();

                if(confirmPW.isEmpty()){
                    return;
                }

                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),confirmPW);

                //re authenticate the user
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //remove the user from authentication
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //deleting the user from the database
                                        database.child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    //remove the profile picture from firebase storage
                                                    StorageReference ProfilePicRef = FirebaseStorage.getInstance().getReference().child("Patient Profile Pictures");
                                                    ProfilePicRef.child(userID + ".jpg").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(UpdatePatientProfile.this, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), PatientLogin.class));
                                                            finish();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                            Toast.makeText(UpdatePatientProfile.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(UpdatePatientProfile.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    } else {
                                        Toast.makeText(UpdatePatientProfile.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(UpdatePatientProfile.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }
}
