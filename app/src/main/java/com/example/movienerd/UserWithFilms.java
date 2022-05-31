package com.example.movienerd;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UserWithFilms {
    @Embedded public  User user;
    @Relation(
            parentColumn = "user_id",
            entityColumn = "film_id",
            associateBy = @Junction(UserFilmCrossRef.class)
    )
    public List<Film> films;
}
