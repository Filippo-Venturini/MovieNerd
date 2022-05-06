package com.example.movienerd.RecyclerView;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienerd.R;

public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView filmImage;

    private OnItemListener itemListener;

    public CardViewHolder(@NonNull View itemView, OnItemListener listener) {
        super(itemView);
        filmImage = itemView.findViewById(R.id.film_imageView);
        itemListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }
}
