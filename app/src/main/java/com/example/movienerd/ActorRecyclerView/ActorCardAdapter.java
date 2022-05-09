package com.example.movienerd.ActorRecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movienerd.Actor;
import com.example.movienerd.Film;
import com.example.movienerd.FilmRecyclerView.FilmCardViewHolder;
import com.example.movienerd.FilmRecyclerView.OnItemListener;
import com.example.movienerd.R;

import java.util.List;

public class ActorCardAdapter extends RecyclerView.Adapter<ActorCardViewHolder>{

    private List<Actor> actors;

    private Activity activity;

    public ActorCardAdapter(List<Actor>actors, Activity activity){
        this.activity = activity;
        this.actors = actors;
    }

    @NonNull
    @Override
    public ActorCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_card_layout, parent, false);
        return new ActorCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorCardViewHolder holder, int position) {
        Actor current  = actors.get(position);

        Glide.with(activity)
                .load(current.getImgUrl())
                .into(holder.actorImage);

        holder.name.setText(current.getName());
        holder.character.setText(current.getCharacter());

    }

    @Override
    public int getItemCount() {
        return actors.size();
    }
}
