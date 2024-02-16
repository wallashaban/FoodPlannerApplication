package com.example.foodplannerapp.meals_feature.presenter;


import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public interface HomePresenter {
    public void addMealToFavouriteUsingFirebase(Meal meal);
    public void removeMealFromFavouriteUsingFirebase(Meal meal);
    public void addMealToPlanUsingFirebase(Plan plan);
    public void removeMealFromPlanUsingFirebase(Plan plan);
    public void getAllMeals(List<Meal> meals);
   public LiveData<Meal> getFavMealById(String id);

    public void addMealToFavourites(Meal meal);

    public void removeMealFromFavourites(Meal meal);

    public void addMealToPlan(Plan plan);

    public void getRandomMeal();

    public void getAllCategories(List<Category> categories);
}
