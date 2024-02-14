package com.example.foodplannerapp.meals_feature.presenter;

import com.example.foodplannerapp.Repository.Repository;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.meals_feature.view.MealDetailsView;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;

import java.util.List;

public class MealDetailsPresenterImpl implements MealDetailsPresenter, MealsNetworkCallBAck {

    MealDetailsView view;
    Repository repository;
    private static MealDetailsPresenterImpl instance = null;
    private MealDetailsPresenterImpl(MealDetailsView view, Repository repository)
    {
        this.view = view;
        this.repository = repository;
    }
    public static MealDetailsPresenterImpl getInstance(MealDetailsView view, Repository repository)
    {
        if(instance == null)
        {
            instance = new MealDetailsPresenterImpl(view,repository);
        }else {
            instance.view=view;
        }
        return instance;
    }
    @Override
    public void getMealByID(String id) {
        repository.getMealByIdNetworkCallBack(this,id);
    }

    @Override
    public void addMealToPlan(Plan plan) {
        repository.addPlan(plan);
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.showData(meals.get(0));

    }

    @Override
    public void onFailureResult(String errorMessage) {
        view.showErrorMessage(errorMessage);
    }
}
