package com.example.foodplannerapp.meals_feature.presenter;

import com.example.foodplannerapp.models.Meal;

public interface RandomMealPresenter {
    public void getRandomMeal();
    public void addRandomMealToFavourites(Meal meal);

    public void removeRandomMealToFavourites(Meal meal);
}
