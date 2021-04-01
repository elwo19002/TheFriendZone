package com.example.thefriendzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListUsersAdapter extends BaseAdapter implements ListAdapter {
    private Context context;
    private List<User> usersList;

    public ListUsersAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int pos) {
        return usersList.get(pos);
    }

    @Override
    public long getItemId(int pos) {

        return 0;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.user_listview, null);
        }

        TextView textViewName = (TextView)view.findViewById(R.id.userName);
        textViewName.setText(usersList.get(position).getFirstName() + " " + usersList.get(position).getLastName());
        TextView textViewBio = (TextView)view.findViewById(R.id.userBio);
        textViewBio.setText("Biography: " + usersList.get(position).getBio());
        TextView textViewInterests = (TextView)view.findViewById(R.id.userInterests);
        textViewInterests.setText("Interests: " + usersList.get(position).getInterests().toString());
        TextView textViewEmail=(TextView)view.findViewById(R.id.userEmail);
        textViewEmail.setText("Contact Information: " + usersList.get(position).getEmail());


        return view;
    }
}
