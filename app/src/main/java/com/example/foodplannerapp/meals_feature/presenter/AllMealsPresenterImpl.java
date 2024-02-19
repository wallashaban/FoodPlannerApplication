package com.example.foodplannerapp.meals_feature.presenter;



import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.Repository.Repository;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.meals_feature.view.AllMealsview;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class AllMealsPresenterImpl implements AllMealsPresenter, MealsNetworkCallBAck {
    private AllMealsview view;
    Repository repository;
    private static AllMealsPresenterImpl instance = null;

    private AllMealsPresenterImpl(AllMealsview view, RepositoryImpl repository) {
        this.view = view;
        this.repository = repository;
    }


    public static AllMealsPresenterImpl getInstance(AllMealsview view, RepositoryImpl repository) {
        if (instance == null) {
            instance = new AllMealsPresenterImpl(view, repository);
        }
        else {
            instance.view=view;
        }
        return instance;
    }

    @Override
    public void getAllMeals() {
        repository.searchMealByFirstLetterNetworkCallBack(this,"b");
    }

    @Override
    public void filterMEalByCategory(String category) {
        repository.filterMealByCategoryNetworkCallBack(this,category);
    }

    @Override
    public void filterMEalByArea(String area) {
        repository.filterMealByAreaNetworkCallBack(this,area);
    }

    @Override
    public void filterMEalByIngredient(String ingredient) {
        repository.filterMealByMainIngredientNetworkCallBack(this,ingredient);
    }

    @Override
    public void addMealToFavourites(Meal meal) {
        repository.addMealToFavourites(meal);
    }

    @Override
    public void removeMealFromFavourites(Meal meal) {
        repository.removeMealFromFavourites(meal);
    }

    @Override
    public Flowable<Meal> getFavMealById(String id) {
       return repository.getFavMealById(id);
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.showData(meals);
    }

    @Override
    public void onFailureResult(String errorMessage) {
        view.showErrorMessage(errorMessage);
    }
}
