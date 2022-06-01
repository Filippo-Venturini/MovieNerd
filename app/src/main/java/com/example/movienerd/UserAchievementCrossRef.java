package com.example.movienerd;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"user_id", "achievement_id"})
public class UserAchievementCrossRef {
    @NonNull
    private int user_id;

    @NonNull
    private int achievement_id;

    public UserAchievementCrossRef(int user_id, int achievement_id){
        this.user_id = user_id;
        this.achievement_id = achievement_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(int achievement_id) {
        this.achievement_id = achievement_id;
    }
}
