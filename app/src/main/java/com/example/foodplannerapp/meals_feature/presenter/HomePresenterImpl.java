package com.example.foodplannerapp.meals_feature.presenter;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.Repository.Repository;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.meals_feature.view.HomeView;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.CategoriesNetworkCallBAck;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;
import com.example.foodplannerapp.network.RandomMealNetworkCallBAck;

import java.util.List;

public class HomePresenterImpl implements HomePresenter, MealsNetworkCallBAck
        , CategoriesNetworkCallBAck, RandomMealNetworkCallBAck {
    private static final String TAG = "repo";
    private HomeView view;
    private Repository repository;

    private static HomePresenterImpl instance = null;

    private HomePresenterImpl(HomeView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }


    public static HomePresenterImpl getInstance(HomeView view, RepositoryImpl repository) {
        if (instance == null) {
            instance = new HomePresenterImpl(view, repository);
        }else {
            instance.view = view;
        }
        return instance;
    }

    @Override
    public void getAllMeals(List<Meal> meals) {
        repository.searchMealByFirstLetterNetworkCallBack(this,
                "c");
    }

    @Override
    public LiveData<Meal> getFavMealById(String id) {
        return repository.getFavMealById(id);
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
    public void addMealToPlan(Plan plan) {
        repository.addPlan(plan);
    }


    @Override
    public void getRandomMeal() {
        repository.getRandomMealsNetworkCallBack(this);
    }

    @Override
    public void getAllCategories(List<Category> categories) {
        repository.getAllCategoriesNetworkCallBack(this);
    }


    @Override
    public void onCategoriesSuccessResult(List<Category> categories) {
        view.showCategoriesData(categories);
    }

    @Override
    public void onCategoriesFailureResult(String errorMessage) {
        view.showCategoriesErrorMessage(errorMessage);
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        Log.i(TAG, "onSuccessResult: Meals");
        view.showMealsData(meals);
    }

    @Override
    public void onFailureResult(String errorMessage) {
        view.showMealsErrorMessage(errorMessage);
    }

    @Override
    public void onRandomMealSuccessResult(List<Meal> meals) {
        view.showRandomMealData(meals.get(0));
    }

    @Override
    public void onRandomMealFailureResult(String errorMessage) {
        view.showRandomMealErrorMessage(errorMessage);
    }
}
