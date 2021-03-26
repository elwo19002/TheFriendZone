package com.example.thefriendzone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** The friendzone class is the main page for our users. They use this to see their matches and communicate with them.*/
public class FriendZone extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private List<User> usersList;
    private User user;
    private Users newUserList;
    private ListView lv;

    DatabaseReference dbInterests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button logout = findViewById(R.id.btnLogout);

        this.user = (User) getIntent().getSerializableExtra("user");
      //  recyclerView = findViewById(R.id.recyclerView); //XML not ready.
       // recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.lv = (ListView) findViewById(R.id.listView);

        usersList = new ArrayList<>();
        this.newUserList = new Users();


        //1. SELECT * FROM Artists
        dbInterests = FirebaseDatabase.getInstance().getReference("interests");
        List<String> x = this.user.getInterests();
        //for (int i = 0; i < x.size(); i++){

 //       Query query = FirebaseDatabase.getInstance().getReference("Users");
                //.orderByChild("interests");
                //.equalTo(String.valueOf(x));



        //adapter = new MatchesAdapter(this, usersList);
        //recyclerView.setAdapter(adapter);

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newUserList = dataSnapshot.getValue(Users.class);
                dataSnapshot.getChildren().forEach(test -> {
                    Map newMap = new HashMap();
                    newMap = (Map) test.getValue();
                    User newUser = new User();
                    if (newMap.get("interests") != null) {
                        newUser.setInterests((ArrayList<String>) newMap.get("interests"));
                        ArrayList<String> initialArrayList = (ArrayList<String>) newUser.getInterests();
                        ArrayList clonedList = new ArrayList();
                        clonedList = (ArrayList) initialArrayList.clone();
                        if (clonedList.retainAll(Collections.singleton(( user.getInterests()).size() > 0))) {
                            newUser.setFirstName((String) newMap.get("firstName"));
                            newUser.setLastName((String) newMap.get("lastName"));
                            newUser.setBio((String) newMap.get("bio"));
                            newUser.setUid((String) newMap.get("uid"));
                            usersList.add(newUser);
                        }
                    }
                    Log.i("Paul", "Just testing");

                });
                displayUsers(usersList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        /*
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersList.clear();
                //if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);
                        usersList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }




        };
 */


 //       query.addValueEventListener(valueEventListener);

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

    public void displayUsers(List<User> usersList) {
        //adapter = new ListAdapter(this, usersList);
        ListUsersAdapter adapter = new ListUsersAdapter(this, usersList);
        this.lv.setAdapter(adapter);
    }
}