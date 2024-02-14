package com.example.foodplannerapp.favourites_feature.view;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;

import java.util.List;

public interface FavouritesView {
    public void setFavouritesList(LiveData<List<Meal>> meals);
}
