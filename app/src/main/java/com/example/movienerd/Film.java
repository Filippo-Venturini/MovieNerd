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
    private String film_id;

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

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public Film(String film_id, String title, String urlPosterImg, String year, String vote){
        this.film_id = film_id;
        this.title = title;
        this.year = year;
        this.urlPosterImg = urlPosterImg;
        this.vote = vote;
    }

    @NonNull
    public String getFilm_id() {
        return film_id;
    }

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
}
