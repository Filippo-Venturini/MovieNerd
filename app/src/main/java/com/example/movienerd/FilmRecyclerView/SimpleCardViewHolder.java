package com.example.movienerd.FilmRecyclerView;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienerd.R;

public class SimpleCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView filmImage;

    private OnItemListener itemListener;

    public SimpleCardViewHolder(@NonNull View itemView, OnItemListener listener){
        super(itemView);
        filmImage = itemView.findViewById(R.id.simpleCard_ImageView);
        itemListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }
}
