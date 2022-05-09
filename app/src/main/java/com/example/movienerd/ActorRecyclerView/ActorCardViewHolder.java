package com.example.movienerd.ActorRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienerd.FilmRecyclerView.OnItemListener;
import com.example.movienerd.R;

public class ActorCardViewHolder extends RecyclerView.ViewHolder{

    ImageView actorImage;

    TextView name;

    TextView character;

    public ActorCardViewHolder(@NonNull View itemView) {
        super(itemView);
        actorImage = itemView.findViewById(R.id.actor_imageView);
        name = itemView.findViewById(R.id.card_actor_name_textView);
        character = itemView.findViewById(R.id.card_actor_character_textView);
    }
}
