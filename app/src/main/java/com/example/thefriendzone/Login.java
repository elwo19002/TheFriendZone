package com.example.thefriendzone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;

/** The login class allows the user to verify their firebase account and access the information that it holds.*/
public class Login extends AppCompatActivity {

    Button buttonCreateProfile, buttonLogin;
    EditText user_email, password;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "MyActivity";

    @Override
    /** On create checks to make sure they entered valid login credentials and allows them in to their account. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonCreateProfile = findViewById(R.id.btnCreateAccount);
        buttonCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            /** On click takes the user to create an account if they don't already have one. */
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateProfile.class));
            }
        });

        user_email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btnLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            /** On click verifies that all of the fields are completed before allowing the user to attempt to login.*/
            public void onClick(View v) {
                // extract or validate
                if(user_email.getText().toString().isEmpty()){
                    user_email.setError("Email is missing");
                    Log.w(TAG, "Email is missing to login");
                    return;
                }

                if(password.getText().toString().isEmpty()){
                    password.setError("Password is missing");
                    Log.w(TAG, "Password is missing to login");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(user_email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    /** On success verifies that the user has an account and allows them to login. */
                    public void onSuccess(AuthResult authResult) {
                        //Successful Login
                        Intent intent = new Intent(getApplicationContext(), FriendZone.class);
                        intent.putExtra("email", user_email.getText().toString());
                        startActivity(intent);
                        Log.i(TAG, "Sucessfully logged in");
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    /** On failure recognizes that the account credentials are invalid and lets the user know.  */
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }

    @Override
    /** On start takes the user to the friendzone if they successfully login. */
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), FriendZone.class);
            intent.putExtra("email",FirebaseAuth.getInstance().getCurrentUser().getEmail() );
            startActivity(intent);
            //startActivity(new Intent(getApplicationContext(), FriendZone.class));
            finish();
        }
    }
}