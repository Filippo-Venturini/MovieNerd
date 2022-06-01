package com.example.movienerd;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UserWithAchievements {
    @Embedded
    public  User user;
    @Relation(
            parentColumn = "user_id",
            entityColumn = "achievement_id",
            associateBy = @Junction(UserAchievementCrossRef.class)
    )
    public List<Achievement> achievements;
}
