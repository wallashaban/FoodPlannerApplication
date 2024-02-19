package com.example.foodplannerapp.meals_feature.presenter;


import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.DialyMeal;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

public interface HomePresenter {
    public void insertDalyMeal(DialyMeal meal);
    public void removeDalyMeal();
    public Maybe<DialyMeal> getDailyMeal(String date);
    public void addMealToFavouriteUsingFirebase(Meal meal);
    public void removeMealFromFavouriteUsingFirebase(Meal meal);
    public void addMealToPlanUsingFirebase(Plan plan);
    public void removeMealFromPlanUsingFirebase(Plan plan);
    public void getAllMeals(List<Meal> meals);
   public Flowable<Meal> getFavMealById(String id);

    public void addMealToFavourites(Meal meal);

    public void removeMealFromFavourites(Meal meal);

    public void addMealToPlan(Plan plan);

    public void getRandomMeal();

    public void getAllCategories(List<Category> categories);
}
