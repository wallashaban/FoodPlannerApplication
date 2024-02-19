package com.example.foodplannerapp.firebase;

import com.example.foodplannerapp.models.Meal;

import java.util.List;

public interface FirebaseMealsNetwokCallBack {
    public void onMealsSuccessResult(List<Meal> meal);

    public void onMealsErrorResult(String errorMessage);
}
