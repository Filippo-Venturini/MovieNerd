package com.example.movienerd.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.movienerd.Film;
import com.example.movienerd.User;
import com.example.movienerd.UserFilmCrossRef;
import com.example.movienerd.UserWithFilms;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class FilmRepository {
    private FilmDAO filmDAO;
    private UserDAO userDAO;
    private UserWithFilmsDAO userWithFilmsDAO;
    private LiveData<List<UserFilmCrossRef>> watchlist;
    private LiveData<List<UserFilmCrossRef>> watchedFilms;
    private LiveData<List<UserWithFilms>> usersWithFilms;
    private LiveData<List<User>> users;
    private LiveData<List<Film>> films;

    public FilmRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        filmDAO = db.filmDAO();
        userDAO = db.userDAO();
        userWithFilmsDAO = db.userWithFilmsDAO();
        watchlist = userWithFilmsDAO.getWatchListId();
        watchedFilms = userWithFilmsDAO.getWatchedFilmsId();
        users = userDAO.getAllUsers();
        usersWithFilms = userWithFilmsDAO.getUsersWithFilms();
        films = filmDAO.getAllFilms();
    }

    public LiveData<List<UserFilmCrossRef>> getWatchList(){
        return watchlist;
    }

    public LiveData<List<UserFilmCrossRef>> getWatchedFilms(){
        return watchedFilms;
    }

    public LiveData<List<User>> getAllUsers() {
        return users;
    }

    public LiveData<List<Film>> getAllFilms(){return  films;}

    public LiveData<List<UserWithFilms>> getUsersWithFilms(){
        return usersWithFilms;
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
                System.out.println(user.getUser_id());
                userDAO.updateUser(user);
            }
        });
    }

    public void addUserWithFilms(UserFilmCrossRef userFilm){
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                userWithFilmsDAO.addUserFilm(userFilm);
            }
        });
    }
}
