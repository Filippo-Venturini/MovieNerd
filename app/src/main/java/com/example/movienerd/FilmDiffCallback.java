package com.example.movienerd;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Locale;

public class FilmDiffCallback extends DiffUtil.Callback {
    private final List<Film> oldFilmList;
    private final List<Film> newFilmList;

    public  FilmDiffCallback(List<Film> oldFilmList, List<Film>newFilmList){
        this.oldFilmList = oldFilmList;
        this.newFilmList = newFilmList;
    }

    @Override
    public int getOldListSize() {
        return oldFilmList.size();
    }

    @Override
    public int getNewListSize() {
        return newFilmList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFilmList.get(oldItemPosition) == newFilmList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Film oldFilm = oldFilmList.get(oldItemPosition);
        final Film newFilm = newFilmList.get(newItemPosition);

        return oldFilm.getFilm_id().equals(newFilm.getFilm_id());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
