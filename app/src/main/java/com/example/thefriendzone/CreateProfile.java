package com.example.thefriendzone;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CreateProfile extends AppCompatActivity {
    EditText createFirstName, createLastName, createEmailAddress, createPassword, confPassword, profileBio;
    Button buttonCreateProfile, buttonTandC;
    CheckBox checkBoxTerms, checkBoxAllowLocation;
    FirebaseAuth fAuth;
    com.example.thefriendzone.MultiSelectInterests profileInterests;
    User user;
    DocumentReference reference;


    private static final String TAG = "MyActivity";
    private static final String TandC = "These are the terms and conditions of the FriendZone. This is the FriendZone. This is a place to find friends, platonic friends. Not friends with benefits or girlfriends or boyfriends. This means any asking for dates, smooches, or snuggles will get you kicked off the app. If that’s what you want, this is not the app for you. Spamming people will also get you kicked out. We will be checking the database for spam accounts.We are not responsible for any stupid actions on the part of our users. Meet in a public place, don’t exchange financial information, etc.";

    @Override
    /** On create takes all of the information input on the screen and initiates its upload to firebase*/
    protected void onCreate(Bundle savedInstanceState) {

        fAuth = FirebaseAuth.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        user = new User();
        createFirstName= findViewById(R.id.createFirstName);
        createLastName= findViewById(R.id.createLastName);
        createEmailAddress=findViewById(R.id.createEmailAddress);
        createPassword=findViewById(R.id.createPassword);
        confPassword=findViewById(R.id.confPassword);
        profileBio=findViewById(R.id.profileBio);
        buttonCreateProfile=findViewById(R.id.buttonCreateProfile);
        checkBoxTerms=findViewById(R.id.checkBoxTerms);
        checkBoxAllowLocation=findViewById(R.id.checkBoxAllowLocation);
        MultiSelectInterests profileInterests = (MultiSelectInterests)findViewById(R.id.profileInterests);
        buttonTandC=findViewById(R.id.btnTandC);

        buttonTandC.setOnClickListener(new View.OnClickListener() {
           @Override
           /** On click verifies that all of the information in the fields are valid and complete */
           public void onClick(View v) {
               Toast.makeText(CreateProfile.this, TandC, Toast.LENGTH_LONG).show();
           }
        });



        buttonCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            /** On click verifies that all of the information in the fields are valid and complete */
            public void onClick(View v) {
                String firstName=createFirstName.getText().toString();
                String lastName=createLastName.getText().toString();
                String bio =profileBio.getText().toString();
                String email=createEmailAddress.getText().toString();
                String password=createPassword.getText().toString();
                String confPass=confPassword.getText().toString();
                CheckBox terms=checkBoxTerms;
                CheckBox location=checkBoxAllowLocation;
                ArrayList<String> selected = (ArrayList<String>) profileInterests.getSelectedStrings();



                if(firstName.isEmpty()){
                    createFirstName.setError("First Name is Required");
                    Log.w(TAG, "No First Name");
                    return;
                }
                if(lastName.isEmpty()){
                    createFirstName.setError("Last Name is Required");
                    Log.w(TAG, "No Last Name");
                    return;
                }
                if (email.isEmpty()){
                    createEmailAddress.setError("Email is Required");
                    Log.w(TAG, "No Email");
                    return;
                }
                if (password.isEmpty()){
                    createPassword.setError("Password is Required");
                    Log.w(TAG, "No Password");
                    return;
                }
                if (confPass.isEmpty()){
                    confPassword.setError("We must confirm your password");
                    Log.w(TAG, "No Confirm Password");
                    return;
                }
                if(!password.equals(confPass)) {
                    confPassword.setError("The passwords must match");
                    Log.w(TAG, "Passwords Don't Match");
                    return;
                }
                if (!(terms.isChecked())){
                    checkBoxTerms.setError("You must agree to the terms");
                    Log.w(TAG, "Did not agree to terms");
                    return;
                }
                if (!(location.isChecked())){
                    checkBoxAllowLocation.setError("We can't find you friends if we don't know where you are");
                    Log.w(TAG, "No Location Permitted");
                }

                Toast.makeText(CreateProfile.this, "Data Validated", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Data Validated");
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(CreateProfile.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    /** On complete finishes making the actual profile and shows a toast when the account is made successfully */
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(CreateProfile.this, "Error creating user", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String user_id = fAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                            reference = db.collection("Users").document(user_id);
                            Log.i(TAG, "User account successfully created");

                            Map newMap = new HashMap();
                            newMap.put("First", firstName);
                            newMap.put("Last", lastName);
                            newMap.put("Bio", bio);
                            newMap.put("Interests", selected);
                            newMap.put("email", email);


                            user.setFirstName(firstName);
                            user.setLastName(lastName);
                            user.setBio(bio);
                            user.setUid(user_id.toString());
                            user.setInterests(selected);
                            user.setEmail(email);

                            current_user_db.setValue(user);
                            reference.set(newMap);
                            Intent intent = new Intent(getApplicationContext(), FriendZone.class);
                            intent.putExtra("email", email);
                            startActivity(intent);

                        }
                    }

                });
            }



        });

    }
}
