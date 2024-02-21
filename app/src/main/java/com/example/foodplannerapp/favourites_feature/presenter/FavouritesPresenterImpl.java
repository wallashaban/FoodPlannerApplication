package com.example.foodplannerapp.favourites_feature.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.favourites_feature.repository.FavouritesRepository;
import com.example.foodplannerapp.favourites_feature.view.FavouritesView;
import com.example.foodplannerapp.firebase_repository.FirebaseCrudRepository;
import com.example.foodplannerapp.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavouritesPresenterImpl implements FavouritesPresenter {
    private FavouritesRepository repository;
    private FavouritesView view;
    private FirebaseCrudRepository crudRepository;
    private static FavouritesPresenterImpl instance;

    private FavouritesPresenterImpl(FavouritesRepository repository,
                                    FavouritesView view,
                                    FirebaseCrudRepository crudRepository) {
        this.repository = repository;
        this.view = view;
        this.crudRepository = crudRepository;
    }

    public static synchronized FavouritesPresenterImpl getInstance(
            FavouritesRepository repository, FavouritesView view,
            FirebaseCrudRepository crudRepository) {
        if (instance == null) {
            instance = new FavouritesPresenterImpl(repository, view,crudRepository);
        } else {
            instance.view = view;
        }
        return instance;
    }

    @Override
    public void getAllFavouritesMeals() {
        Flowable<List<Meal>> meals = repository.getAllFavMeals();
        view.setFavouritesList(meals);
    }

    @Override
    public void removeMealFromFavourites(Meal meal) {
        repository.removeMealFromFavourites(meal);
    }

    @Override
    public void removeMealFromFavouritesUsingFirebase(Meal meal) {
        crudRepository.removeMealFromFavouriteUsingFirebase(meal);
    }
}
