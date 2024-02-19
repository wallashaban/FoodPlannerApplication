package com.example.foodplannerapp.network;

import com.example.foodplannerapp.models.Meal;

import java.util.List;

public interface RandomMealNetworkCallBAck {
    public void onRandomMealSuccessResult(List<Meal> meals);

    public void onRandomMealFailureResult(String errorMessage);

    public void onNetworkFailure(String errorMessage);

}
