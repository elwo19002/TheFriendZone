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
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private String email;
    private String zipcode;

    DatabaseReference dbInterests;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button logout = findViewById(R.id.btnLogout);
        this.email = getIntent().getStringExtra("email");
        this.lv = (ListView) findViewById(R.id.listView);
        usersList = new ArrayList<>();
        this.newUserList = new Users();

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(child -> {
                    Map newMap = new HashMap();
                    newMap = (Map) child.getValue();
                    User userAdd = new User();
                    userAdd.setFirstName((String) newMap.get("firstName"));
                    userAdd.setLastName((String) newMap.get("lastName"));
                    userAdd.setBio((String) newMap.get("bio"));
                    userAdd.setUid((String) newMap.get("uid"));
                    userAdd.setEmail((String) newMap.get("email"));
                    userAdd.setInterests((ArrayList<String>) newMap.get("interests"));
                    userAdd.setZipCode((String) newMap.get("zipCode"));
                    newUserList.getUsers().add(userAdd);
                    if (userAdd.getEmail() != null) {
                        if (userAdd.getEmail().equals(email)) {
                            user = userAdd;
                        }
                    }
                });
                if (user != null) {
                    matchLocator(newUserList);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void matchLocator (Users users) {

       List<String> x = this.user.getInterests();
       users.getUsers().forEach(userCheck -> {
            User newUser = new User();
            if (!userCheck.getUid().equals(user.getUid())) {
                if (userCheck.getInterests() != null) {
                    if (userCheck.getZipCode() != null) {
                        if (user.getZipCode().equals(userCheck.getZipCode())) {

                            ArrayList<String> initialArrayList = (ArrayList<String>) userCheck.getInterests();
                            ArrayList clonedList = new ArrayList();
                            clonedList = (ArrayList) initialArrayList.clone();
                            clonedList.retainAll(user.getInterests());
                            if (clonedList.size() > 0) {
                                newUser.setZipCode((String) userCheck.getZipCode());
                                newUser.setFirstName((String) userCheck.getFirstName());
                                newUser.setLastName((String) userCheck.getLastName());
                                newUser.setBio((String) userCheck.getBio());
                                newUser.setUid((String) userCheck.getUid());
                                newUser.setEmail((String) userCheck.getEmail());
                                newUser.setInterests((ArrayList<String>) userCheck.getInterests());
                                usersList.add(newUser);
                            }
                        }
                    }
                }
            }
            Log.i("Yay", "Its working");

        });
        displayUsers(usersList);
    }

    public void displayUsers(List<User> usersList) {
        //adapter = new ListAdapter(this, usersList);
        ListUsersAdapter adapter = new ListUsersAdapter(this, usersList);
        this.lv.setAdapter(adapter);
    }

}