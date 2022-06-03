package com.example.movienerd.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.movienerd.Achievement;
import com.example.movienerd.Film;
import com.example.movienerd.User;
import com.example.movienerd.UserAchievementCrossRef;
import com.example.movienerd.UserFilmCrossRef;
import com.example.movienerd.UserWithAchievements;
import com.example.movienerd.UserWithFilms;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class FilmRepository {
    private FilmDAO filmDAO;
    private UserDAO userDAO;
    private UserWithFilmsDAO userWithFilmsDAO;
    private AchievementDAO achievementDAO;
    private UserWithAchievementDAO userWithAchievementDAO;
    private LiveData<List<UserFilmCrossRef>> watchlist;
    private LiveData<List<UserFilmCrossRef>> watchedFilms;
    private LiveData<List<User>> users;
    private LiveData<List<Film>> films;
    private LiveData<List<Achievement>> achievements;
    private LiveData<List<UserAchievementCrossRef>> usersWithAchievements;

    public FilmRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        filmDAO = db.filmDAO();
        userDAO = db.userDAO();
        userWithFilmsDAO = db.userWithFilmsDAO();
        achievementDAO = db.achievementDAO();
        userWithAchievementDAO = db.userWithAchievementDAO();
        watchlist = userWithFilmsDAO.getWatchListId();
        watchedFilms = userWithFilmsDAO.getWatchedFilmsId();
        users = userDAO.getAllUsers();
        films = filmDAO.getAllFilms();
        achievements = achievementDAO.getAllAchievements();
        usersWithAchievements = userWithAchievementDAO.getUsersWithAchievements();
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

    public LiveData<List<Achievement>> getAllAchievement(){return  achievements;}

    public LiveData<List<UserAchievementCrossRef>> getUsersWithAchievements(){
        return usersWithAchievements;
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

    public void addAchievement(Achievement achievement){
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                achievementDAO.addAchievement(achievement);
            }
        });
    }

    public void updateUser(User user){
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.updateUser(user);
            }
        });
    }

    public void updateUserFilms(UserFilmCrossRef userFilms){
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                userWithFilmsDAO.updateUserFilms(userFilms);
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

    public void addUserWithAchievements(UserAchievementCrossRef userAchievement){
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                userWithAchievementDAO.addUserAchievement(userAchievement);
            }
        });
    }
}
