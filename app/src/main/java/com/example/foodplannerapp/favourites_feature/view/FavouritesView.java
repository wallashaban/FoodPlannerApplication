package com.example.foodplannerapp.favourites_feature.view;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavouritesView {
    public void setFavouritesList(Flowable<List<Meal>> meals);
}
