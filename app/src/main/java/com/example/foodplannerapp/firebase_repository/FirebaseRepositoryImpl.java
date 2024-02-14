package com.example.foodplannerapp.firebase_repository;

import com.example.foodplannerapp.firebase.FirebaseNetworkCallback;
import com.example.foodplannerapp.firebase.FirebaseRemoteDataSource;
import com.example.foodplannerapp.models.AuthParameters;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

public class FirebaseRepositoryImpl implements FirebaseRepository{
   private FirebaseRemoteDataSource dataSource;
   private static FirebaseRepositoryImpl instance= null;

   private FirebaseRepositoryImpl(FirebaseRemoteDataSource dataSource)
   {
       this.dataSource = dataSource;
   }
   public static synchronized FirebaseRepositoryImpl getInstance(FirebaseRemoteDataSource
                                                                 dataSource)
   {
       if(instance == null)
       {
           instance = new FirebaseRepositoryImpl(dataSource);
       }
       return instance;
   }

    @Override
    public void registerUserWithEmailAndPassword(AuthParameters parameters, FirebaseNetworkCallback networkCallback) {
        dataSource.registerUserWithEmailAndPassword(parameters,networkCallback);
    }

    @Override
    public void loginUserWithEmailAndPassword(AuthParameters parameters,FirebaseNetworkCallback networkCallback) {
        dataSource.loginUserWithEmailAndPassword(parameters,networkCallback);
    }

    @Override
    public void addMealToFavourite(Meal meal) {
        dataSource.addMealToFavourite(meal);
    }

    @Override
    public void removeMealFromFavourite(Meal meal) {
        dataSource.removeMealFromFavourite(meal);
    }

    @Override
    public void addMealToPlan(Plan plan) {
        dataSource.addMealToPlan(plan);
    }

    @Override
    public void removeMealFromPlan(Plan plan) {
        dataSource.removeMealFromPlan(plan);
    }
}
