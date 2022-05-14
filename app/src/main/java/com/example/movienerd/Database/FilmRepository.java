package com.example.movienerd.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.movienerd.Film;

import java.util.List;

public class FilmRepository {
    private FilmDAO filmDAO;
    private LiveData<List<Film>> watchlist;
    private LiveData<List<Film>> watchedFilms;

    public FilmRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        filmDAO = db.filmDAO();
        watchlist = filmDAO.getWatchList();
        watchedFilms = filmDAO.getWatchedFilms();
    }

    public LiveData<List<Film>> getWatchList(){
        return watchlist;
    }

    public LiveData<List<Film>> getWatchedFilms(){
        return watchedFilms;
    }

    public void addFilm(Film film){
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                filmDAO.addFilm(film);
            }
        });
    }
}
