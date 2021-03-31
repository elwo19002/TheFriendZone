package com.example.thefriendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class termsandconditions extends AppCompatActivity {
    Button buttonCreateProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsandconditions);
        buttonCreateProfile=findViewById(R.id.buttonCreateProfile);
    }
    buttonCreateProfile.setOnClickListener(new View.OnClickListener(){
    public void onClick(View v){


            }
        });