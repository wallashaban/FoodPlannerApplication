package com.example.foodplannerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface FavouritesDAO {
    @Query("SELECT * FROM " + Constants.FAV_TABLE)
    Flowable<List<Meal>> getAllFavMeals();

    @Query("SELECT * FROM " + Constants.FAV_TABLE + " Where mealId=:id")
    Flowable<Meal> getFavMealById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMealToFavourites(Meal meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllFavourites(List<Meal> meals);

    @Delete
    void removeMealFromFavourites(Meal meal);

    @Query("DELETE FROM " + Constants.FAV_TABLE)
    void deleteAllFavMeal();

}
