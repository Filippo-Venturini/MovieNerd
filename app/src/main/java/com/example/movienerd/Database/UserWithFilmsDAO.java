package com.example.movienerd.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.movienerd.Film;
import com.example.movienerd.User;
import com.example.movienerd.UserFilmCrossRef;
import com.example.movienerd.UserWithFilms;

import java.util.List;

@Dao
public interface UserWithFilmsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUserFilm(UserFilmCrossRef user);

    @Transaction
    @Query("SELECT * FROM UserFilmCrossRef WHERE isInWatchlist == 1")
    LiveData<List<UserFilmCrossRef>> getWatchListId();

    @Transaction
    @Query("SELECT * FROM UserFilmCrossRef WHERE isWatched == 1")
    LiveData<List<UserFilmCrossRef>> getWatchedFilmsId();
}
