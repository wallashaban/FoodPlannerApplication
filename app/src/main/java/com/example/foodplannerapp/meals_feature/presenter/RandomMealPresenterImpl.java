package com.example.foodplannerapp.meals_feature.presenter;

import com.example.foodplannerapp.Repository.Repository;
import com.example.foodplannerapp.firebase_repository.FirebaseCrudRepository;
import com.example.foodplannerapp.meals_feature.view.RandomMealView;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;
import com.example.foodplannerapp.network.RandomMealNetworkCallBAck;

import java.util.List;

public class RandomMealPresenterImpl implements RandomMealPresenter, RandomMealNetworkCallBAck {
    private RandomMealView view;
    private Repository repository;
    private FirebaseCrudRepository firebaseCrudRepository;

    private static RandomMealPresenterImpl instance;

    private RandomMealPresenterImpl(RandomMealView view , Repository repository,
                                    FirebaseCrudRepository firebaseCrudRepository)
    {
        this.view = view;
        this.repository = repository;
        this.firebaseCrudRepository = firebaseCrudRepository;
    }
    @Override
    public void getRandomMeal() {
        repository.getRandomMealsNetworkCallBack(this);
    }

    public static synchronized RandomMealPresenterImpl getInstance(
            RandomMealView view , Repository repository,FirebaseCrudRepository firebaseCrudRepository)
    {
        if(instance == null)
        {
            instance = new RandomMealPresenterImpl(view,repository,firebaseCrudRepository);
        }
        return  instance;
    }
    @Override
    public void addRandomMealToFavourites(Meal meal) {
        repository.addMealToFavourites(meal);
    }

    @Override
    public void removeRandomMealToFavourites(Meal meal) {
        repository.removeMealFromFavourites(meal);
    }


    @Override
    public void onRandomMealSuccessResult(List<Meal> meals) {
        view.showData(meals.get(0));
    }

    @Override
    public void onRandomMealFailureResult(String errorMessage) {

    view.showErrorMessage(errorMessage);

}
    @Override
    public void addMealToFavouriteUsingFirebase(Meal meal) {
        firebaseCrudRepository.addMealToFavouriteUsingFirebase(meal);
    }

    @Override
    public void removeMealFromFavouriteUsingFirebase(Meal meal) {
        firebaseCrudRepository.removeMealFromFavouriteUsingFirebase(meal);
    }

    @Override
    public void addMealToPlanUsingFirebase(Plan plan) {
        firebaseCrudRepository.addMealToPlanUsingFirebase(plan);
    }

    @Override
    public void removeMealFromPlanUsingFirebase(Plan plan) {
        firebaseCrudRepository.removeMealFromPlanUsingFirebase(plan);
    }

}
