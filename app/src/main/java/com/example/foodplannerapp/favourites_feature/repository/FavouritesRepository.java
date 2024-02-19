package com.example.foodplannerapp.favourites_feature.repository;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavouritesRepository {
    public Flowable<List<Meal>> getAllFavMeals();

    public void addMealToFavourites(Meal meal);

    public void removeMealFromFavourites(Meal meal);

    public void addAllFavourites(List<Meal> meals);

    public void addAllPlans(List<Plan> plans);
}
