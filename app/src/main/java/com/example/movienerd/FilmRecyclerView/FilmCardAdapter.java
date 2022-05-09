package com.example.movienerd.FilmRecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movienerd.Film;
import com.example.movienerd.R;

import java.util.List;

public class FilmCardAdapter extends RecyclerView.Adapter<FilmCardViewHolder> {

    private List<Film> cardItemList;

    private Activity activity;

    private  OnItemListener listener;

    public FilmCardAdapter(OnItemListener listener, List<Film> cardItemList, Activity activity){
        this.listener = listener;
        this.cardItemList = cardItemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FilmCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_card_layout, parent, false);
        return new FilmCardViewHolder(layoutView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmCardViewHolder holder, int position) {
        Film current  = cardItemList.get(position);

        Glide.with(activity)
                .load(current.getUrlPosterImg())
                .into(holder.filmImage);

        holder.title.setText(current.getTitle());
        holder.year.setText(current.getYear());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }
}
