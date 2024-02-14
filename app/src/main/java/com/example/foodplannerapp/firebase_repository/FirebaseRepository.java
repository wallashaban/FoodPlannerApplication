package com.example.foodplannerapp.firebase_repository;

import com.example.foodplannerapp.firebase.FirebaseNetworkCallback;
import com.example.foodplannerapp.models.AuthParameters;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

public interface FirebaseRepository {
    public void registerUserWithEmailAndPassword(AuthParameters parameters,FirebaseNetworkCallback networkCallback);
    public void loginUserWithEmailAndPassword(AuthParameters parameters, FirebaseNetworkCallback networkCallback);
    public void addMealToFavourite(Meal meal);
    public void removeMealFromFavourite(Meal meal);
    public void addMealToPlan(Plan plan);
    public void removeMealFromPlan(Plan plan);
}
