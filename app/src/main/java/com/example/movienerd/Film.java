package com.example.movienerd;

public class Film {
    private String id;

    private String name;

    private String urlPreviewImg;

    private String urlPosterImg;

    public Film(String id, String name, String urlPosterImg){
        this.id = id;
        this.name = name;
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
