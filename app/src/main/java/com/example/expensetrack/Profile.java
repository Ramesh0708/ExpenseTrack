package com.example.expensetrack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Profile extends AppCompatActivity {


    private static final int CHOOSE_IMAGE = 101 ;
    ImageView imageView;
    EditText editText;
    TextView useremail;
    Button button;
    Uri uriprofileImage;
    String profileImageUrl;
    private FirebaseAuth mAuth;
    String item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        imageView = (ImageView) findViewById(R.id.userphoto);
        editText = (EditText) findViewById(R.id.username);
        useremail = (TextView) findViewById(R.id.email);
        button = (Button) findViewById(R.id.saveButon);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setTitle(R.string.app_name_profile);









        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImageChooser();

            }
        });


        loadUserInformation();

        findViewById(R.id.saveButon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveUserInformation();

            }


        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logOut) {
            mAuth.signOut();
            Intent intent = new Intent(Profile.this, MainActivity.class);
            startActivity(intent);
        }


        else  if (item.getItemId() == R.id.Graph) {
            Intent i = new Intent( Profile.this, ExpensePieGraph.class);
            startActivity(i);
        }

        return true;
    }






    private void saveUserInformation() {

        String displayName = editText.getText().toString();

        if (displayName.isEmpty()){

            editText.setError("Name is Required");
            editText.requestFocus();
            return;
            }
        FirebaseUser  user =  mAuth.getCurrentUser();

        if (user!=null  && profileImageUrl!=null) {
        UserProfileChangeRequest  profile = new UserProfileChangeRequest.Builder()
                 .setDisplayName(displayName)
                .setPhotoUri(Uri.parse(profileImageUrl))
                .build();

        user.updateProfile(profile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                       Toast.makeText(Profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                                   }
                                               });


        }



        }


    private void showImageChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData()!=null){

           uriprofileImage = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriprofileImage);

                imageView.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();




            } catch (IOException e) {
                e.printStackTrace();
            }

        }





    }

    private void uploadImageToFirebaseStorage() {

        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/"+ System.currentTimeMillis() + ".jpg");

        if(uriprofileImage != null){
            profileImageRef.putFile(uriprofileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {



                                final Uri downloadUrl = uri;
                            }
                        });





                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }


    private void loadUserInformation() {
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {


        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl().toString())
                    .into(imageView);


        }

        if ( user.getDisplayName() != null){

            editText.setText("" +user.getDisplayName());


        }

            if ( user.getEmail() != null){

                useremail.setText(user.getEmail());


        }














    }
}}



