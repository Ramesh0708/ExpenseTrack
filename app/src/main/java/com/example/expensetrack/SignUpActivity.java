package com.example.expensetrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements  View.OnClickListener {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
//                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    TextView fullNameField, emailField, passwordField;
    Button signUpBtn, cancelBtn;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle(R.string.app_name_signUp);
        loadAllViews();


        fullNameField = (TextView) findViewById(R.id.fullNameField);
        emailField = (TextView) findViewById(R.id.emailField);
        passwordField = (TextView) findViewById(R.id.passwordField);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        profilebtn = (Button) findViewById(R.id.profileButton);

        mAuth = FirebaseAuth.getInstance();


    }






    public void loadAllViews() {
        fullNameField = (TextView) findViewById(R.id.fullNameField);
        emailField = (TextView) findViewById(R.id.emailField);
        passwordField = (TextView) findViewById(R.id.passwordField);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(this);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);

    }





    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.signUpBtn:

                registerUser();
                
                closeKeyboard();

                break;

            case R.id.cancelBtn:
                startActivity(new Intent(this,MainActivity.class));

                break;

//            case R.id.profileButton:
//                startActivity(new Intent(this,Profile.class));
//
//                break;

        }

    }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }

    private void registerUser() {

        String fullname = fullNameField.getText().toString().trim();
        String Email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();


        if (fullname.isEmpty()){
            fullNameField.setError(" Field can't be empty ");
            fullNameField.requestFocus();
            return;
        }

        if (Email.isEmpty()){

            emailField.setError(" Field can't be empty");
            emailField.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            emailField.setError(" Please enter a Valid Email");
            emailField.requestFocus();
            return;

        }


        if (password.isEmpty()){

            passwordField.setError(" Field can't be empty");
            passwordField.requestFocus();
            return;
        }
        else if (!PASSWORD_PATTERN.matcher(password).matches()){
            passwordField.setError(" Password too weak");
            passwordField.requestFocus();

            return ;

        }



//        if (password.length()<6){
//            passwordField.setError(" Minimum length of password should be 6 ");
//            passwordField.requestFocus();
//            return;
//        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);


                }

                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException){

                        Toast.makeText(getApplicationContext(),"You are already Registered", Toast.LENGTH_SHORT).show();


                    }

                    else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });







    }

    }



