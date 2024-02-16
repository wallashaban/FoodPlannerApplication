package com.example.foodplannerapp.meals_feature.presenter;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

public interface MealDetailsPresenter {
    public void getMealByID(String id);
    public void addMealToPlan(Plan plan);
    public void addMealToFavourites(Meal meal);
    public void removeMealFromFavourites(Meal meal);
    public void removeMealFromPlan(Plan plan);
    public void addMealToFavouriteUsingFirebase(Meal meal);
    public void removeMealFromFavouriteUsingFirebase(Meal meal);
    public void addMealToPlanUsingFirebase(Plan plan);
    public void removeMealFromPlanUsingFirebase(Plan plan);
}
