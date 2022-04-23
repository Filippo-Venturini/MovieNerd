package com.example.movienerd.RecyclerView;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienerd.R;

public class CardViewHolder extends RecyclerView.ViewHolder {

    ImageView filmImage;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        filmImage = itemView.findViewById(R.id.film_imageView);
    }
}
