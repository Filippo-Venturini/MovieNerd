package com.example.movienerd.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.movienerd.UserAchievementCrossRef;
import com.example.movienerd.UserFilmCrossRef;
import com.example.movienerd.UserWithAchievements;
import com.example.movienerd.UserWithFilms;

import java.util.List;

@Dao
public interface UserWithAchievementDAO {
    @Transaction
    @Query("SELECT * FROM UserAchievementCrossRef")
    LiveData<List<UserAchievementCrossRef>> getUsersWithAchievements();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUserAchievement(UserAchievementCrossRef user);
}
