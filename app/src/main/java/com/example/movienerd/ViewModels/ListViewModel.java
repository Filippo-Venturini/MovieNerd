package com.example.movienerd.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movienerd.Database.FilmRepository;
import com.example.movienerd.Film;
import com.example.movienerd.User;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private final MutableLiveData<Film> filmSelected = new MutableLiveData<>();
    public  MutableLiveData<List<Film>> homeFilms;
    public  MutableLiveData<List<Film>> searchedFilms;
    private  final FilmRepository repository;
    private LiveData<List<Film>> watchList;
    private LiveData<List<Film>> watchedFilms;

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = new FilmRepository(application);
        watchList = repository.getWatchList();
        watchedFilms = repository.getWatchedFilms();
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

    public LiveData<List<Film>> getWatchList(){
        return watchList;
    }

    public LiveData<List<Film>> getWatchedFilms(){
        return watchedFilms;
    }

    public void addFilm(Film film){
        repository.addFilm(film);
    }

    public void addUser(User user){repository.addUser(user);}

    public User getUser(int id){return repository.getUser(id);}

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
