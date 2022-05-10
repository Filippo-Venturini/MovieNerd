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
    public  MutableLiveData<List<Film>> films;

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public  MutableLiveData<Film> getFilmSelected(){
        return filmSelected;
    }

    public void setFilmSelected(Film film){
        filmSelected.setValue(film);
    }

    public LiveData<List<Film>> getFilms(){
        if(films == null){
            films = new MutableLiveData<>();
        }
        return films;
    }

    public void addFilm(Film film){
        ArrayList<Film> list = new ArrayList<>();
        list.add(film);
        if(films.getValue() != null){
            list.addAll(films.getValue());
        }
        films.setValue(list);
    }
}
