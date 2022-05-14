package com.example.movienerd.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movienerd.Database.FilmRepository;
import com.example.movienerd.Film;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private final MutableLiveData<Film> filmSelected = new MutableLiveData<>();
    public  MutableLiveData<List<Film>> homeFilms;
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

    public LiveData<List<Film>> getWatchList(){
        return watchList;
    }

    public LiveData<List<Film>> getWatchedFilms(){
        return watchedFilms;
    }

    public void addFilm(Film film){
        repository.addFilm(film);
    }

    public void addHomeFilm(Film film){
        ArrayList<Film> list = new ArrayList<>();
        if(homeFilms.getValue() != null){
            list.addAll(homeFilms.getValue());
        }
        list.add(film);
        homeFilms.setValue(list);
    }

    public void clearHomeList(){
        if(homeFilms.getValue() != null){
            homeFilms.getValue().clear();
        }
    }

    public void clearWatchList(){
        if(watchList.getValue() != null){
            watchList.getValue().clear();
        }
    }
}
