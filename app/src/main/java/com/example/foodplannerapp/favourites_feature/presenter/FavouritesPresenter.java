package com.example.foodplannerapp.favourites_feature.presenter;

import com.example.foodplannerapp.models.Meal;

public interface FavouritesPresenter {
    public void getAllFavouritesMeals();

    public void removeMealFromFavourites(Meal meal);
}
