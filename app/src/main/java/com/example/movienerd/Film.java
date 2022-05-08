package com.example.movienerd;

public class Film {
    private String id;

    private String title;

    private String urlPreviewImg;

    private String urlPosterImg;

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
