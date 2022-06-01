package com.example.movienerd.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.movienerd.Achievement;
import com.example.movienerd.Film;

import java.util.List;

@Dao
public interface AchievementDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addAchievement(Achievement achievement);

    @Transaction
    @Query("SELECT * FROM achievement")
    LiveData<List<Achievement>> getAllAchievements();
}
