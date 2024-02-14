package com.example.foodplannerapp.database;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public interface FavouritesLocalDataSource {
    public LiveData<List<Meal>> getAllFavMeals();
    public LiveData<Meal>getFavMealById(String id);

    public void addMealToFavourites(Meal meal);

    public void removeMealFromFavourites(Meal meal);

    ///////////////////////////////////////////////////
    LiveData<List<Plan>> getAllPlans();
    void addPlan(Plan plan);
    void removePlan(Plan plan);
    void updatePlan(Plan plan);
    LiveData<Plan> getPlaneByDate(String date);
}
