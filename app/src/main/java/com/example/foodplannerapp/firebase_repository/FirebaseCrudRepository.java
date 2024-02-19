package com.example.foodplannerapp.firebase_repository;

import com.example.foodplannerapp.firebase.FirebaseMealsNetwokCallBack;
import com.example.foodplannerapp.firebase.FirebasePlanNetworkCallBack;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

public interface FirebaseCrudRepository {
    public void addMealToFavouriteUsingFirebase(Meal meal);

    public void removeMealFromFavouriteUsingFirebase(Meal meal);

    public void addMealToPlanUsingFirebase(Plan plan);

    public void removeMealFromPlanUsingFirebase(Plan plan);

}
