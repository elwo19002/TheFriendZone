package com.example.thefriendzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/** The friendzone class is the main page for our users. They use this to see their matches and communicate with them.*/
public class FriendZone extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private MatchesAdapter adapter;
    private List<User> usersList;
    private User user;

    DatabaseReference dbInterests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button logout = findViewById(R.id.btnLogout);

        this.user = (User) getIntent().getSerializableExtra("user");
        recyclerView = findViewById(R.id.recyclerView); //XML not ready.
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersList = new ArrayList<>();


        //1. SELECT * FROM Artists
        dbInterests = FirebaseDatabase.getInstance().getReference("interests");
        List<String> x = this.user.getInterests();
        //for (int i = 0; i < x.size(); i++){

        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("interests")
                .equalTo(String.valueOf(x));




        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);
                        usersList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        query.addValueEventListener(valueEventListener);
        adapter = new MatchesAdapter(this, usersList);
        recyclerView.setAdapter(adapter);

        //This is the logout button.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}