package com.example.movienerd.FilmRecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movienerd.Achievement;
import com.example.movienerd.AchievementDiffCallback;
import com.example.movienerd.Film;
import com.example.movienerd.FilmDiffCallback;
import com.example.movienerd.R;

import java.util.ArrayList;
import java.util.List;

public class AchievementCardAdapter extends RecyclerView.Adapter<AchievementViewHolder> {
    private List<Achievement> achievementsList = new ArrayList<>();
    private List<Achievement> achievementsListNotFiltered = new ArrayList<>();
    private Activity activity;

    public AchievementCardAdapter(Activity activity){
        this.activity = activity;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_card_layout, parent, false);
        return new AchievementViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement current  = achievementsList.get(position);

        Glide.with(activity)
                .load(getImage(current.getImageName()))
                .into(holder.image);

        holder.title.setText(current.getTitle());
        holder.description.setText(current.getDescription());
    }

    private int getImage(String imageName) {
        int drawableResourceId = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
        return drawableResourceId;
    }

    public void setData(List<Achievement> list){
        this.achievementsList = new ArrayList<>(list);
        this.achievementsListNotFiltered = new ArrayList<>(list);

        final AchievementDiffCallback diffCallback = new AchievementDiffCallback(this.achievementsList, list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        diffResult.dispatchUpdatesTo(this);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.achievementsList.size();
    }
}
