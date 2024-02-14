package com.example.foodplannerapp.network;

import com.example.foodplannerapp.models.Category;

import java.util.List;

public interface CategoriesNetworkCallBAck {
    public void onCategoriesSuccessResult(List<Category> meals);

    public void onCategoriesFailureResult(String errorMessage);
}
