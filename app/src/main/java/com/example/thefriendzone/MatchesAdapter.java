package com.example.thefriendzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ArtistViewHolder> {

    private Context mCtx;
    private List<User> usersList;

    public MatchesAdapter(Context mCtx, List<User> usersList) {
        this.mCtx = mCtx;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_users, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.textViewName.setText(user.getFirstName());
        holder.textViewName.setText(user.getLastName());
        holder.textViewBio.setText("Bio: " + user.getBio());
        holder.textViewInterests.setText("Interests: " + user.getInterests());
        //holder.textViewAge.setText("Age: " + artist.age);
        //holder.textViewCountry.setText("Country: " + artist.country);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewBio, textViewInterests;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewBio = itemView.findViewById(R.id.text_view_bio);
            textViewInterests = itemView.findViewById(R.id.text_view_interests);
            //textViewCountry = itemView.findViewById(R.id.text_view_country);
        }
    }
}
