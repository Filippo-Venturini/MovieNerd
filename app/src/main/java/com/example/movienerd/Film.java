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

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public Film(String id, String title, String urlPosterImg, String year){
        this.id = id;
        this.title = title;
        this.year = year;
        this.urlPosterImg = urlPosterImg;
    }

    public String getId(){return this.id;}

    public String getUrlPreviewImg() {
        return urlPreviewImg;
    }

    public void setUrlPreviewImg(String urlPreviewImg) {
        this.urlPreviewImg = urlPreviewImg;
    }

    public String getUrlPosterImg() {
        return urlPosterImg;
    }

    public void setUrlPosterImg(String urlPosterImg) {
        this.urlPosterImg = urlPosterImg;
    }
}
