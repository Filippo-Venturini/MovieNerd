package com.example.movienerd.FilmRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movienerd.Film;
import com.example.movienerd.R;

public class FilmCardViewHolder extends RecyclerView.ViewHolder{

    ImageView filmImage;

    TextView title;

    TextView year;

    private OnItemClickListener itemListener;

    public FilmCardViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        filmImage = itemView.findViewById(R.id.film_imageView);
        title = itemView.findViewById(R.id.card_title_textView);
        year = itemView.findViewById(R.id.card_year_textView);
        itemListener = listener;
    }

    public void bind(final Film film, final OnItemClickListener listener){
        Glide.with(itemView.getContext())
                .load(film.getUrlPosterImg())
                .into(this.filmImage);

        this.title.setText(film.getTitle());
        this.year.setText(film.getYear());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(film);
            }
        });

    }
}
