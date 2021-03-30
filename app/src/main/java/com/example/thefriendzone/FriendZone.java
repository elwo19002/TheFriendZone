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

    DatabaseReference dbInterests;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button logout = findViewById(R.id.btnLogout);

        //this.user = (User) getIntent().getSerializableExtra("user");
        this.email = getIntent().getStringExtra("email");
      //  recyclerView = findViewById(R.id.recyclerView); //XML not ready.
       // recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.lv = (ListView) findViewById(R.id.listView);

        usersList = new ArrayList<>();
        this.newUserList = new Users();


        //1. SELECT * FROM Artists
       // dbInterests = FirebaseDatabase.getInstance().getReference("interests");
       // List<String> x = this.user.getInterests();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void matchLocator (Users users) {

       List<String> x = this.user.getInterests();
       users.getUsers().forEach(userCheck -> {
            User newUser = new User();
            if (!userCheck.getUid().equals(user.getUid())) {
                if (userCheck.getInterests() != null) {
                    newUser.setInterests((ArrayList<String>) userCheck.getInterests());
                    ArrayList<String> initialArrayList = (ArrayList<String>) newUser.getInterests();
                    ArrayList clonedList = new ArrayList();
                    clonedList = (ArrayList) initialArrayList.clone();
                    if (clonedList.retainAll(Collections.singleton((this.user.getInterests()).size() > 0))) {
                        newUser.setFirstName((String) userCheck.getFirstName());
                        newUser.setLastName((String) userCheck.getLastName());
                        newUser.setBio((String) userCheck.getBio());
                        newUser.setUid((String) userCheck.getUid());
                        newUser.setEmail((String) userCheck.getEmail());
                        usersList.add(newUser);
                    }
                }
            }
            Log.i("Paul", "Just testing");

        });
        displayUsers(usersList);
    }

    public void displayUsers(List<User> usersList) {
        //adapter = new ListAdapter(this, usersList);
        ListUsersAdapter adapter = new ListUsersAdapter(this, usersList);
        this.lv.setAdapter(adapter);
    }

}