package com.example.movienerd.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movienerd.Film;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private final MutableLiveData<Film> filmSelected = new MutableLiveData<>();
    public  MutableLiveData<List<Film>> homeFilms;
    public MutableLiveData<List<Film>> watchList = new MutableLiveData<>();

    public ListViewModel(@NonNull Application application) {
        super(application);
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
        if(watchList == null){
            watchList = new MutableLiveData<>();
        }
        return watchList;
    }

    public void addFilmToWatchList(Film film){
        ArrayList<Film> list = new ArrayList<>();
        list.add(film);
        if(watchList.getValue() != null){
            list.addAll(watchList.getValue());
        }
        watchList.setValue(list);
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
