package com.example.thefriendzone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;


public class CreateProfile extends AppCompatActivity {
    EditText createFirstName, createLastName, createEmailAddress, createPassword, confPassword, profileBio;
    Button buttonCreateProfile;
    CheckBox checkBoxTerms, checkBoxAllowLocation;
    FirebaseAuth fAuth;
    com.example.thefriendzone.MultiSelectInterests profileInterests;


    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        fAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_profile);

        createFirstName= findViewById(R.id.createFirstName);
        createLastName= findViewById(R.id.createLastName);
        createEmailAddress=findViewById(R.id.createEmailAddress);
        createPassword=findViewById(R.id.createPassword);
        confPassword=findViewById(R.id.confPassword);
        profileBio=findViewById(R.id.profileBio);
        buttonCreateProfile=findViewById(R.id.buttonCreateProfile);
        checkBoxTerms=findViewById(R.id.checkBoxTerms);
        checkBoxAllowLocation=findViewById(R.id.checkBoxAllowLocation);
        profileInterests=findViewById(R.id.profileInterests);



        buttonCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile newProfile= new Profile();



                //if (email!= null) {
                   // firstName = email.getFirstName();
                   // lastName = email.getLastName();
                    //bio = email.getbio();
                //}

                if(newProfile.firstName.isEmpty()){
                    createFirstName.setError("First Name is Required");
                    Log.w(TAG, "No First Name");
                    return;
                }
                if(newProfile.lastName.isEmpty()){
                    createFirstName.setError("Last Name is Required");
                    Log.w(TAG, "No Last Name");
                    return;
                }
                if (newProfile.email.isEmpty()){
                    createEmailAddress.setError("Email is Required");
                    Log.w(TAG, "No Email");
                    return;
                }
                if (newProfile.password.isEmpty()){
                    createPassword.setError("Password is Required");
                    Log.w(TAG, "No Password");
                    return;
                }
                if (newProfile.confPass.isEmpty()){
                    confPassword.setError("We must confirm your password");
                    Log.w(TAG, "No Confirm Password");
                    return;
                }
                if(!newProfile.password.equals(newProfile.confPass)) {
                    confPassword.setError("The passwords must match");
                    Log.w(TAG, "Passwords Don't Match");
                    return;
                }
                if (!(newProfile.terms.isChecked())){
                    checkBoxTerms.setError("You must agree to the terms");
                    Log.w(TAG, "Did not agree to terms");
                    return;
                }
                if (!(newProfile.location.isChecked())){
                    checkBoxAllowLocation.setError("We can't find you friends if we don't know where you are");
                    Log.w(TAG, "No Location Permitted");
                }

                Toast.makeText(CreateProfile.this, "Data Validated", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Data Validated");
                fAuth.createUserWithEmailAndPassword(newProfile.email,newProfile.password).addOnCompleteListener(CreateProfile.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(CreateProfile.this, "Error creating user", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String user_id = fAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                            Log.i(TAG, "User account successfully created");

                            Map newMap = new HashMap();
                            newMap.put("First", newProfile.firstName);
                            newMap.put("Last", newProfile.lastName);
                            newMap.put("Bio", newProfile.bio);

                            current_user_db.setValue(newMap);
                            startActivity(new Intent(getApplicationContext(), Login.class));

                        }
                    }

                });
            }

//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        //send user to next page. Eventually send to matches, for now FriendZone
//                        startActivity(new Intent(getApplicationContext(), FriendZone.class));
//                        Toast.makeText(CreateProfile.this, "Profile Created", Toast.LENGTH_SHORT).show();
//                        finish();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(CreateProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                });



        });

    }
}
