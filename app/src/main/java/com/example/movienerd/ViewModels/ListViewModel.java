package com.example.movienerd.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movienerd.Achievement;
import com.example.movienerd.Database.FilmRepository;
import com.example.movienerd.Film;
import com.example.movienerd.User;
import com.example.movienerd.UserAchievementCrossRef;
import com.example.movienerd.UserFilmCrossRef;
import com.example.movienerd.UserWithAchievements;
import com.example.movienerd.UserWithFilms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private final MutableLiveData<Film> filmSelected = new MutableLiveData<>();
    public  MutableLiveData<List<Film>> homeFilms;
    public  MutableLiveData<List<Film>> searchedFilms;
    private  final FilmRepository repository;
    private LiveData<List<UserFilmCrossRef>> watchListId;
    private LiveData<List<UserFilmCrossRef>> watchedFilmsId;
    private LiveData<List<User>> allUsers;
    private LiveData<List<Film>> allFilms;
    private LiveData<List<Achievement>> allAchievements;
    private LiveData<List<UserAchievementCrossRef>> usersWithAchievement;

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = new FilmRepository(application);
        watchListId = repository.getWatchList();
        watchedFilmsId = repository.getWatchedFilms();
        allUsers = repository.getAllUsers();
        allFilms = repository.getAllFilms();
        allAchievements = repository.getAllAchievement();
        usersWithAchievement = repository.getUsersWithAchievements();
    }

    public  MutableLiveData<Film> getFilmSelected(){
        return filmSelected;
    }

    public void setFilmSelected(Film film){
        filmSelected.setValue(film);
    }

    public LiveData<List<Film>> getHomeFilms(){
        if(homeFilms == null){
            homeFilms = new MutableLiveData<>();
        }
        return homeFilms;
    }

    public LiveData<List<Film>> getSearchedFilms(){
        if(searchedFilms == null){
            searchedFilms = new MutableLiveData<>();
        }
        return searchedFilms;
    }

    public LiveData<List<UserFilmCrossRef>> getWatchList(){
        return watchListId;
    }

    public LiveData<List<UserFilmCrossRef>> getWatchedFilms(){
        return watchedFilmsId;
    }

    public LiveData<List<User>> getAllUsers(){return allUsers;}

    public LiveData<List<Film>> getAllFilms(){return  allFilms;}

    public LiveData<List<UserAchievementCrossRef>> getUsersWithAchievement(){return usersWithAchievement;}

    public LiveData<List<Achievement>> getAllAchievements(){return allAchievements;}

    public void addAchievement(Achievement achievement){ repository.addAchievement(achievement);}

    public void addFilm(Film film){
        repository.addFilm(film);
    }

    public void addUser(User user){repository.addUser(user);}

    public void addUserFilm(UserFilmCrossRef userFilm){repository.addUserWithFilms(userFilm);}

    public void addUserAchievement(UserAchievementCrossRef userAchievement){ repository.addUserWithAchievements(userAchievement);}

    public void updateUser(User user){ repository.updateUser(user);}

    public void updateUserFilms(UserFilmCrossRef userFilms){repository.updateUserFilms(userFilms);}

    public void addHomeFilm(Film film){
        ArrayList<Film> list = new ArrayList<>();
        if(homeFilms.getValue() != null){
            list.addAll(homeFilms.getValue());
        }
        list.add(film);
        homeFilms.setValue(list);
    }

    public void addSearchedFilm(Film film){
        ArrayList<Film> list = new ArrayList<>();
        if(searchedFilms.getValue() != null){
            list.addAll(searchedFilms.getValue());
        }
        list.add(film);
        searchedFilms.setValue(list);
    }

    public void clearHomeList(){
        if(homeFilms.getValue() != null){
            homeFilms.getValue().clear();
        }
    }

    public void clearSearchedList(){
        if(searchedFilms.getValue() != null){
            searchedFilms.getValue().clear();
        }
    }
}
