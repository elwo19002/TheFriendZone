package com.example.thefriendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CreateProfile extends AppCompatActivity {
    EditText createFirstName, createLastName, createEmailAddress, createPassword, confPassword, profileBio;
    Button buttonCreateProfile;
    CheckBox checkBoxTerms, checkBoxAllowLocation;

    Firebase Auth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        //fAuth=FirebaseAuth.getInstance();

        buttonCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName=createFirstName.getText().toString();
                String lastName=createLastName.getText().toString();
                String email=createEmailAddress.getText().toString();
                String password=createPassword.getText().toString();
                String confPass=confPassword.getText().toString();
                CheckBox terms=checkBoxTerms;
                CheckBox location=checkBoxAllowLocation;

                if(firstName.isEmpty()){
                    createFirstName.setError("First Name is Required");
                    return;
                }
                if(lastName.isEmpty()){
                    createFirstName.setError("Last Name is Required");
                    return;
                }
                if (email.isEmpty()){
                    createEmailAddress.setError("Email is Required");
                    return;
                }
                if (password.isEmpty()){
                    createPassword.setError("Password is Required");
                    return;
                }
                if (confPass.isEmpty()){
                    confPassword.setError("We must confirm your password");
                    return;
                }
                if (!(terms.isChecked())){
                    checkBoxTerms.setError("You must agree to the terms");
                    return;
                }
                if (!(location.isChecked())){
                    checkBoxAllowLocation.setError("We can't find you friends if we don't know where you are");
                }
                Toast.makeText(CreateProfile.this, "Data Validated", Toast.LENGTH_SHORT).show();
                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>(){

                }

            }

        });

    }
}