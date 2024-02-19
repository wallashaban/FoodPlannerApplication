package com.example.foodplannerapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplannerapp.Shared.Constants;
import com.example.foodplannerapp.models.DialyMeal;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

@Dao

public interface DailyMealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDailyMeal(DialyMeal meal);

    @Query("DELETE FROM " + Constants.DAILY_MEAL_TABLE)
    void removeDailyMeal();

    @Query("SELECT * FROM " + Constants.DAILY_MEAL_TABLE + " WHERE date = :date")
    Maybe<DialyMeal> getDailyMeal(String date);
}
