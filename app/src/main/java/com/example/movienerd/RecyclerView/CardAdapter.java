package com.example.movienerd.RecyclerView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienerd.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<String> cardItemList;

    private Activity activity;

    public CardAdapter(List<String> cardItemList, Activity activity){
        this.cardItemList = cardItemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new CardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String currentImagePath = cardItemList.get(position);

        Drawable drawable = ContextCompat.getDrawable(activity, activity.getResources()
                .getIdentifier(currentImagePath, "drawable", activity.getPackageName()));

        System.out.println(drawable);

        holder.filmImage.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }
}
