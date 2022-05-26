package com.example.movienerd.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.movienerd.Film;
import com.example.movienerd.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);

    @Transaction
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();


    @Transaction
    @Query("SELECT * FROM user WHERE user_id = :id")
    User getUser(int id);

}