package com.example.foodplannerapp.meals_feature.presenter;

import com.example.foodplannerapp.Repository.Repository;
import com.example.foodplannerapp.meals_feature.view.RandomMealView;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;
import com.example.foodplannerapp.network.RandomMealNetworkCallBAck;

import java.util.List;

public class RandomMealPresenterImpl implements RandomMealPresenter, RandomMealNetworkCallBAck {
    private RandomMealView view;
    private Repository repository;

    private static RandomMealPresenterImpl instance;

    private RandomMealPresenterImpl(RandomMealView view , Repository repository)
    {
        this.view = view;
        this.repository = repository;
    }
    @Override
    public void getRandomMeal() {
        repository.getRandomMealsNetworkCallBack(this);
    }

    public static synchronized RandomMealPresenterImpl getInstance(RandomMealView view , Repository repository)
    {
        if(instance == null)
        {
            instance = new RandomMealPresenterImpl(view,repository);
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
}
