package com.example.foodplannerapp.firebase_repository;

import com.example.foodplannerapp.firebase.FirebaseMealsNetwokCallBack;
import com.example.foodplannerapp.firebase.FirebasePlanNetworkCallBack;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSource;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

public class FirebaseCrudRepositoryImpl implements FirebaseCrudRepository{
    private FirebaseRemoteDataSource dataSource;
    private static FirebaseCrudRepositoryImpl instance= null;

    private FirebaseCrudRepositoryImpl(FirebaseRemoteDataSource dataSource)
    {
        this.dataSource = dataSource;
    }
    public static synchronized FirebaseCrudRepositoryImpl getInstance(FirebaseRemoteDataSource
                                                                              dataSource)
    {
        if(instance == null)
        {
            instance = new FirebaseCrudRepositoryImpl(dataSource);
        }
        return instance;
    }

    @Override
    public void addMealToFavouriteUsingFirebase(Meal meal) {
        dataSource.addMealToFavourite(meal);
    }

    @Override
    public void removeMealFromFavouriteUsingFirebase(Meal meal) {
        dataSource.removeMealFromFavourite(meal);
    }

    @Override
    public void addMealToPlanUsingFirebase(Plan plan) {
        dataSource.addMealToPlan(plan);
    }

    @Override
    public void removeMealFromPlanUsingFirebase(Plan plan) {
        dataSource.removeMealFromPlan(plan);
    }

    @Override
    public void getFavMealsFromFirebase(FirebaseMealsNetwokCallBack netwokCallBack) {
        dataSource.getAllFavMealFromFirebase(netwokCallBack);
    }

    @Override
    public void getPlansFromFirebase(FirebasePlanNetworkCallBack networkCallBack) {
        dataSource.getAllPlansFromFirebase(networkCallBack);
    }
}
