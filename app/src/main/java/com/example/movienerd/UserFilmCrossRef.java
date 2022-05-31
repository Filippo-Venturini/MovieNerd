package com.example.movienerd;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"user_id", "film_id"})
public class UserFilmCrossRef {
    @NonNull
    private int user_id;
    @NonNull
    private String film_id;

    @ColumnInfo(name="isInWatchlist")
    private boolean isInWatchlist;

    @ColumnInfo(name="isWatched")
    private boolean isWatched;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFilm_id() {
        return film_id;
    }

    public void setFilm_id(String film_id) {
        this.film_id = film_id;
    }

    public boolean isInWatchlist() {
        return isInWatchlist;
    }

    public void setInWatchlist(boolean inWatchlist) {
        isInWatchlist = inWatchlist;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setWatched(boolean watched) {
        isWatched = watched;
    }

    public UserFilmCrossRef(int user_id, String film_id){
        this.user_id = user_id;
        this.film_id = film_id;
    }
}
