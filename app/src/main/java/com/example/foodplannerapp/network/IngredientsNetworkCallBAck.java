package com.example.foodplannerapp.network;

import com.example.foodplannerapp.models.Ingredient;

import java.util.List;

public interface IngredientsNetworkCallBAck {
    public void onIngredientsSuccessResult(List<Ingredient> meals);

    public void onIngredientsFailureResult(String errorMessage);
}
