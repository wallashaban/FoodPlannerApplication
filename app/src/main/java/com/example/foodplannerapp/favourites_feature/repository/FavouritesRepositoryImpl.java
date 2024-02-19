package com.example.foodplannerapp.favourites_feature.repository;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.database.FavouritesLocalDataSource;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavouritesRepositoryImpl implements FavouritesRepository {
    FavouritesLocalDataSource localDataSource;
    private static FavouritesRepositoryImpl instance = null;

    private FavouritesRepositoryImpl(FavouritesLocalDataSource localDataSource) {
        this.localDataSource = localDataSource; // Why not initialized it here instead?
    }

    public static synchronized FavouritesRepositoryImpl getInstance(FavouritesLocalDataSource localDataSource) {
        if (instance == null) {
            instance = new FavouritesRepositoryImpl(localDataSource);
        }
        return instance;
    }

    @Override
    public Flowable<List<Meal>> getAllFavMeals() {
        return localDataSource.getAllFavMeals();
    }

    @Override
    public void addMealToFavourites(Meal meal) {
        localDataSource.addMealToFavourites(meal);
    }

    @Override
    public void removeMealFromFavourites(Meal meal) {
        localDataSource.removeMealFromFavourites(meal);
    }

    @Override
    public void addAllFavourites(List<Meal> meals) {
        localDataSource.insertAllFavouries(meals);
    }

    @Override
    public void addAllPlans(List<Plan> plans) {
        localDataSource.insertAllPlans(plans);
    }
}
