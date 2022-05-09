package com.example.movienerd.FilmRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienerd.R;

public class FilmCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView filmImage;

    TextView title;

    TextView year;

    private OnItemListener itemListener;

    public FilmCardViewHolder(@NonNull View itemView, OnItemListener listener) {
        super(itemView);
        filmImage = itemView.findViewById(R.id.film_imageView);
        title = itemView.findViewById(R.id.card_title_textView);
        year = itemView.findViewById(R.id.card_year_textView);
        itemListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }
}
