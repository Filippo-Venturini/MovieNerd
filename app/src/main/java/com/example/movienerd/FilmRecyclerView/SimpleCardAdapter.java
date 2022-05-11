package com.example.movienerd.FilmRecyclerView;

import android.app.Activity;
import android.location.GnssAntennaInfo;
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

public class SimpleCardAdapter  extends RecyclerView.Adapter<SimpleCardViewHolder> {
    private Activity activity;

    private  OnItemListener listener;

    private List<Film> filmList = new ArrayList<>();

    private List<Film> filmListNotFiltered = new ArrayList<>();

    public SimpleCardAdapter(OnItemListener listener, Activity activity){
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimpleCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_card_layout, parent, false);
        return new SimpleCardViewHolder(layoutView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleCardViewHolder holder, int position) {
        Film current  = filmList.get(position);

        Glide.with(activity)
                .load(current.getUrlPosterImg())
                .into(holder.filmImage);
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
