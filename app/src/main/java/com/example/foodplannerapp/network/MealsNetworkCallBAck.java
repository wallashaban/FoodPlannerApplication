package com.example.foodplannerapp.network;

import com.example.foodplannerapp.models.Meal;

import java.util.List;

public interface MealsNetworkCallBAck {
    public void onSuccessResult(List<Meal> meals);

    public void onFailureResult(String errorMessage);
}
