package com.example.foodplannerapp.firebase;

import com.example.foodplannerapp.models.Meal;

import java.util.List;

public interface FirebaseMealsNetwokCallBack {
    public void onSuccessResult(List<Meal> meal);
    public void onErrorResult(String errorMessage);
}
