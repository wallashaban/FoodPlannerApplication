package com.example.foodplannerapp.favourites_feature.repository;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;

import java.util.List;

public interface FavouritesRepository {
    public LiveData<List<Meal>> getAllFavMeals();

    public void addMealToFavourites(Meal meal);

    public void removeMealFromFavourites(Meal meal);
}