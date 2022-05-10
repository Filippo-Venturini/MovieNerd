package com.example.movienerd.FilmRecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movienerd.Film;
import com.example.movienerd.FilmDiffCallback;
import com.example.movienerd.R;

import java.util.ArrayList;
import java.util.List;

public class FilmCardAdapter extends RecyclerView.Adapter<FilmCardViewHolder> {

    private List<Film> filmList = new ArrayList<>();

    private Activity activity;

    private  OnItemListener listener;

    private List<Film> filmListNotFiltered = new ArrayList<>();

    public FilmCardAdapter(OnItemListener listener, Activity activity){
        this.listener = listener;
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
        Film current  = filmList.get(position);

        Glide.with(activity)
                .load(current.getUrlPosterImg())
                .into(holder.filmImage);

        holder.title.setText(current.getTitle());
        holder.year.setText(current.getYear());
    }

    public void setData(List<Film> list){
        this.filmList = new ArrayList<>(list);
        this.filmListNotFiltered = new ArrayList<>(list);

        final FilmDiffCallback diffCallback = new FilmDiffCallback(this.filmList, list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        diffResult.dispatchUpdatesTo(this);
    }

    public Film getFilmSelected(int position){
        return filmList.get(position);
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }
}
