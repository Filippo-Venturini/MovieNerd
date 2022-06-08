package com.example.movienerd;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="achievement")
public class Achievement {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "achievement_id")
    @NonNull
    private int achievement_id;

    @ColumnInfo(name = "achievement_image_name")
    private String imageName;

    @ColumnInfo(name = "achievement_title")
    private String title;

    @ColumnInfo(name = "achievement_description")
    private String description;

    public Achievement(int achievement_id, String imageName, String title, String description){
        this.achievement_id = achievement_id;
        this.imageName = imageName;
        this.title = title;
        this.description = description;
    }

    @NonNull
    public int getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(@NonNull int achievement_id) {
        this.achievement_id = achievement_id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
