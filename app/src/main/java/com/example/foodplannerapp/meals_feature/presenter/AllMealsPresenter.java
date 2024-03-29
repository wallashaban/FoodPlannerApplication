package com.example.foodplannerapp.meals_feature.presenter;


import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;

import io.reactivex.rxjava3.core.Flowable;

public interface AllMealsPresenter {
    public void filterMEalByCategory(String category);

    public void filterMEalByArea(String area);

    public void filterMEalByIngredient(String ingredient);

    public void getAllMeals();

    public void addMealToFavourites(Meal meal);

    public void removeMealFromFavourites(Meal meal);

    public Flowable<Meal> getFavMealById(String id);
}
