package com.example.movienerd;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    @NonNull
    private int user_id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "isLogged")
    private boolean isLogged;

    @ColumnInfo(name = "filmInWatchlist")
    private int filmInWatchlist;

    @ColumnInfo(name = "filmWatched")
    private int filmWatched;

    public void toggleWatchListCounter(boolean increment){
        if(increment){
            this.filmInWatchlist++;
        }else{
            this.filmInWatchlist--;
        }
    }

    public void toggleFilmWatchedCounter(boolean increment){
        if(increment){
            this.filmWatched++;
        }else{
            this.filmWatched--;
        }
    }

    public int getFilmWatched() {
        return filmWatched;
    }

    public void setFilmWatched(int filmWatched) {
        this.filmWatched = filmWatched;
    }

    public int getFilmInWatchlist() {
        return filmInWatchlist;
    }

    public void setFilmInWatchlist(int filmInWatchlist) {
        this.filmInWatchlist = filmInWatchlist;
    }

    public Boolean getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(Boolean logged) {
        isLogged = logged;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(){};

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
}
