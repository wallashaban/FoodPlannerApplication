package com.example.foodplannerapp.favourites_feature.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.favourites_feature.repository.FavouritesRepository;
import com.example.foodplannerapp.favourites_feature.view.FavouritesView;
import com.example.foodplannerapp.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavouritesPresenterImpl implements FavouritesPresenter {
    private FavouritesRepository repository;
    private FavouritesView view;
    private static FavouritesPresenterImpl instance;

    private FavouritesPresenterImpl(FavouritesRepository repository, FavouritesView view) {
        this.repository = repository;
        this.view = view;
    }

    public static synchronized FavouritesPresenterImpl getInstance(FavouritesRepository repository, FavouritesView view) {
        if (instance == null) {
            instance = new FavouritesPresenterImpl(repository, view);
        }else {
            instance.view=view;
        }
        return instance;
    }

    @Override
    public /*LiveData<List<Meal>>*/ void getAllFavouritesMeals() {
        Flowable<List<Meal>> meals = repository.getAllFavMeals();
        view.setFavouritesList(meals);
        //return repository.getAllFavMeals();
    }

    @Override
    public void removeMealFromFavourites(Meal meal) {
        repository.removeMealFromFavourites(meal);
    }
}
