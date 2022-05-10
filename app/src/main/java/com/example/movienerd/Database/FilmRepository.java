package com.example.movienerd.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.movienerd.Film;

import java.util.List;

public class FilmRepository {
    private FilmDAO filmDAO;
    private LiveData<List<Film>> filmList;

    public FilmRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        filmDAO = db.filmDAO();
        filmList = filmDAO.getFilms();
    }

    public LiveData<List<Film>> getFilmList(){
        return filmList;
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
