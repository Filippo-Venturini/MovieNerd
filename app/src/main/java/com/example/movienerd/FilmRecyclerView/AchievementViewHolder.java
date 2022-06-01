package com.example.movienerd.FilmRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienerd.R;

public class AchievementViewHolder extends RecyclerView.ViewHolder {
    ImageView image;

    TextView title;

    TextView description;

    public AchievementViewHolder(@NonNull View itemView) {
        super(itemView);

        this.image = itemView.findViewById(R.id.achievement_imageView);
        this.title = itemView.findViewById(R.id.achievementTitle_textView);
        this.description = itemView.findViewById(R.id.achievementDescription_textView);
    }
}
