package com.example.foodplannerapp.meals_feature.presenter;


import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;

public interface  AllMealsPresenter {
    public void filterMEalByCategory(String category);

    public void getAllMeals();
    public void addMealToFavourites(Meal meal);

    public void removeMealFromFavourites(Meal meal);
    public LiveData<Meal> getFavMealById(String id);
}
