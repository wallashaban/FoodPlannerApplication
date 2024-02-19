package com.example.foodplannerapp.firebase;

import android.content.Context;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.models.AuthParameters;

import java.util.List;

public interface FirebaseRemoteDataSource {
    public void registerUserWithEmailAndPassword(AuthParameters parameters, FirebaseAuthNetworkCallback networkCallback);

    public void loginUserWithEmailAndPassword(AuthParameters parameters, FirebaseAuthNetworkCallback networkCallback);

    public void addMealToFavourite(Meal meal);

    public void removeMealFromFavourite(Meal meal);

    public void addMealToPlan(Plan plan);

    public void removeMealFromPlan(Plan plan);

    public void getAllPlansFromFirebase(FirebasePlanNetworkCallBack networkCallBack);

    public void getAllFavMealFromFirebase(FirebaseMealsNetwokCallBack netwokCallBack);

    public void logOut();
}
