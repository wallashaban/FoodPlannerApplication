package com.example.foodplannerapp.meals_feature.presenter;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

public interface RandomMealPresenter {
    public void addMealToFavouriteUsingFirebase(Meal meal);
    public void removeMealFromFavouriteUsingFirebase(Meal meal);
    public void addMealToPlanUsingFirebase(Plan plan);
    public void removeMealFromPlanUsingFirebase(Plan plan);
    public void getRandomMeal();
    public void addRandomMealToFavourites(Meal meal);

    public void removeRandomMealToFavourites(Meal meal);
}
