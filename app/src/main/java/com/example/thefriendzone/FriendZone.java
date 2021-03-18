package com.example.thefriendzone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
/** The friendzone class is the main page for our users. They use this to see their matches and communicate with them.*/
public class FriendZone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}