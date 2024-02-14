package com.example.foodplannerapp.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

@androidx.room.Database(entities = {Meal.class, Plan.class}, version = 3, exportSchema = false)

public abstract class Database extends RoomDatabase {
    private static Database instance = null;
    Context context;

    public abstract FavouritesDAO getFavouritesDao();// Why public?
    public abstract PlanDAO getPlanDao();
    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Database.class, // why .class?
                    Constants.FAV_TABLE)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
