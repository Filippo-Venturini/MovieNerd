package com.example.movienerd.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.movienerd.Film;
import com.example.movienerd.User;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class FilmRepository {
    private FilmDAO filmDAO;
    private UserDAO userDAO;
    private LiveData<List<Film>> watchlist;
    private LiveData<List<Film>> watchedFilms;
    private LiveData<List<User>> users;

    public FilmRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        filmDAO = db.filmDAO();
        userDAO = db.userDAO();
        watchlist = filmDAO.getWatchList();
        watchedFilms = filmDAO.getWatchedFilms();
        users = userDAO.getAllUsers();
    }

    public LiveData<List<Film>> getWatchList(){
        return watchlist;
    }

    public LiveData<List<Film>> getWatchedFilms(){
        return watchedFilms;
    }

    public LiveData<List<User>> getAllUsers() {
        return users;
    }

    public void addFilm(Film film){
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                filmDAO.addFilm(film);
            }
        });
    }

    public void addUser(User user){
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.addUser(user);
            }
        });
    }

    public void updateUser(User user){
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(user.getId());
                userDAO.updateUser(user);
            }
        });
    }
}
