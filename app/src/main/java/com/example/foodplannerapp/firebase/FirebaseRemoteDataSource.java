package com.example.foodplannerapp.firebase;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.models.AuthParameters;

public interface FirebaseRemoteDataSource {
    public void registerUserWithEmailAndPassword(AuthParameters parameters,FirebaseNetworkCallback networkCallback);
    public void loginUserWithEmailAndPassword(AuthParameters parameters,FirebaseNetworkCallback networkCallback);
    public void addMealToFavourite(Meal meal);
    public void removeMealFromFavourite(Meal meal);
    public void addMealToPlan(Plan plan);
    public void removeMealFromPlan(Plan plan);
}
