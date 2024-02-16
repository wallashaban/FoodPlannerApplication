package com.example.foodplannerapp.meals_feature.presenter;

import com.example.foodplannerapp.Repository.Repository;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.firebase_repository.FirebaseCrudRepository;
import com.example.foodplannerapp.meals_feature.view.MealDetailsView;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;

import java.util.List;

public class MealDetailsPresenterImpl implements MealDetailsPresenter, MealsNetworkCallBAck {

    MealDetailsView view;
    Repository repository;
    private FirebaseCrudRepository firebaseCrudRepository;
    private static MealDetailsPresenterImpl instance = null;
    private MealDetailsPresenterImpl(MealDetailsView view, Repository repository,
                                     FirebaseCrudRepository firebaseCrudRepository
                                     )
    {
        this.view = view;
        this.repository = repository;
        this.firebaseCrudRepository = firebaseCrudRepository;
    }
    public static MealDetailsPresenterImpl getInstance(MealDetailsView view,
                                                       Repository repository,
                                                       FirebaseCrudRepository firebaseCrudRepository)
    {
        if(instance == null)
        {
            instance = new MealDetailsPresenterImpl(view,repository,firebaseCrudRepository);
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
    public void addMealToFavourites(Meal meal) {
        repository.addMealToFavourites(meal);
    }

    @Override
    public void removeMealFromFavourites(Meal meal) {
        repository.removeMealFromFavourites(meal);
    }

    @Override
    public void removeMealFromPlan(Plan plan) {
        repository.removePlan(plan);
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.showData(meals.get(0));

    }

    @Override
    public void onFailureResult(String errorMessage) {
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
