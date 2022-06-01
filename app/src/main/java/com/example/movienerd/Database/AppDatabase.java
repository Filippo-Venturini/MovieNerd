package com.example.movienerd.Database;

import android.content.Context;
import android.text.style.IconMarginSpan;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movienerd.Achievement;
import com.example.movienerd.Film;
import com.example.movienerd.User;
import com.example.movienerd.UserAchievementCrossRef;
import com.example.movienerd.UserFilmCrossRef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Film.class, User.class, UserFilmCrossRef.class, Achievement.class, UserAchievementCrossRef.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FilmDAO filmDAO();

    public abstract UserDAO userDAO();

    public abstract UserWithFilmsDAO userWithFilmsDAO();

    public abstract AchievementDAO achievementDAO();

    public  abstract UserWithAchievementDAO userWithAchievementDAO();

    private static  volatile AppDatabase INSTANCE;

    static  final ExecutorService executor = Executors.newFixedThreadPool(4);

    static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "movie_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
