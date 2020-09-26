package com.example.expensetrack;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;





public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private static final int MY_REQUEST_CODE =  7117;  // any number you want
    TextView emailField, passwordField;
    Button loginBtn, signBtn;
    private FirebaseAuth mAuth;
//    private SignInButton signInButton;

//    List<AuthUI.IdpConfig> providers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    //Init providers
//        providers = Arrays.asList(
//              new AuthUI.IdpConfig.EmailBuilder().build(), // Email Builder
//                new AuthUI.IdpConfig.PhoneBuilder().build(), // Phone Builder
//                new AuthUI.IdpConfig.FacebookBuilder().build(), // Facebook Builder
//                new AuthUI.IdpConfig.GoogleBuilder().build() // Google Builder
//
//        );
//
//        showSignInOptioons();


        










        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni.isConnected()) {
            mAuth = FirebaseAuth.getInstance();

            loadAllViews();
            FirebaseApp.initializeApp(this);
        } else {
            Toast.makeText(MainActivity.this, R.string.toast_no_internet, Toast.LENGTH_LONG).show();
        }
    }

//    private void showSignInOptioons() {
//        startActivityForResult(
//                AuthUI.getInstance().createSignInIntentBuilder()
//                .setAvailableProviders(providers)
//                .setTheme(R.style.MyTheme)
//                .build() , MY_REQUEST_CODE
//
//        );
//
//
//
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == MY_REQUEST_CODE){
//            IdpResponse response = IdpResponse.fromResultIntent(data);
//            if (requestCode == RESULT_OK){
//
//                // GET USER
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                // show email on toast
//                Toast.makeText(this,""+user.getEmail(),Toast.LENGTH_SHORT ).show();
//
//            }
//            else {
//                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public void loadAllViews() {
        emailField = (TextView) findViewById(R.id.emailField);
        passwordField = (TextView) findViewById(R.id.passwordField);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        signBtn = (Button) findViewById(R.id.signUpBtn);
        signBtn.setOnClickListener(this);
    }



    @Override
    protected void onStart() {
        super.onStart();


        if (mAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(this,ExpenseActivity.class));
        }

    }




//       MobileAds.initialize(this, new OnInitializationCompleteListener() {
//        @Override
//        public void onInitializationComplete(InitializationStatus initializationStatus) {
//        }
//    });

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginBtn) {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            closeKeyboard();

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, R.string.toast_empty_values, Toast.LENGTH_LONG).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("test", "signInWithEmail:onComplete:" + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    finish();
                                    Log.w("test", "signInWithEmail:failed", task.getException());
                                    Toast.makeText(MainActivity.this, R.string.toast_loginFailed, Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        } else if (v.getId() == R.id.signUpBtn) {
            Intent intent = new Intent(this,SignUpActivity.class);

            startActivity(intent);
        }







    }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }
}




