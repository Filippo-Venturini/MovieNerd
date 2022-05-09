package com.example.movienerd;

public class Actor {
    private String name;

    private String character;

    private String imgUrl;

    public Actor(String name, String character, String imgUrl){
        this.name = name;
        this.character = character;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
