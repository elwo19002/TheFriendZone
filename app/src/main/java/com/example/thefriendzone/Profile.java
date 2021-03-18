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

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

/** The profile class is the bridge that stores all of the users account information on firebase. */
public class Profile {
    EditText createFirstName, createLastName, createEmailAddress, createPassword, confPassword, profileBio;
    Button buttonCreateProfile;
    CheckBox checkBoxTerms, checkBoxAllowLocation;
    FirebaseAuth fAuth;
    com.example.thefriendzone.MultiSelectInterests profileInterests;

    private static final String TAG = "MyActivity";


                String firstName=createFirstName.getText().toString();
                String lastName=createLastName.getText().toString();
                String bio =profileBio.getText().toString();
                String email=createEmailAddress.getText().toString();
                String password=createPassword.getText().toString();
                String confPass=confPassword.getText().toString();
                CheckBox terms=checkBoxTerms;
                CheckBox location=checkBoxAllowLocation;





    }

