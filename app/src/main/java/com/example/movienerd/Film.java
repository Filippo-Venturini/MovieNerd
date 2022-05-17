package com.example.movienerd;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="film")
public class Film {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "film_id")
    @NonNull
    private String id;

    @ColumnInfo(name = "film_title")
    private String title;

    @ColumnInfo(name = "film_urlPreviewImg")
    private String urlPreviewImg;

    @ColumnInfo(name = "film_urlPosterImg")
    private String urlPosterImg;

    @ColumnInfo(name = "film_year")
    private String year;

    @ColumnInfo(name = "film_duration")
    private String duration;

    @ColumnInfo(name="film_vote")
    private String vote;

    @ColumnInfo(name="isInWatchlist")
    private boolean isInWatchlist;

    @ColumnInfo(name="isWatched")
    private boolean isWatched;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public Film(String id, String title, String urlPosterImg, String year, String vote){
        this.id = id;
        this.title = title;
        this.year = year;
        this.urlPosterImg = urlPosterImg;
        this.vote = vote;
    }

    public String getId(){return this.id;}

    public String getVote() {
        return vote;
    }

    public String getUrlPreviewImg() {
        return urlPreviewImg;
    }

    public void setUrlPreviewImg(String urlPreviewImg) {
        this.urlPreviewImg = urlPreviewImg;
    }

    public String getUrlPosterImg() {
        return urlPosterImg;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration){this.duration = duration;}

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
}
