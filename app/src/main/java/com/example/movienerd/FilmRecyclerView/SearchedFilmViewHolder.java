package com.example.movienerd.FilmRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienerd.R;

public class SearchedFilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView filmImage;

    TextView title;

    TextView year;

    private OnItemListener itemListener;

    public SearchedFilmViewHolder(@NonNull View itemView, OnItemListener listener){
        super(itemView);
        filmImage = itemView.findViewById(R.id.searchedFilm_img);
        title = itemView.findViewById(R.id.searchedFilm_title);
        itemListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }
}
