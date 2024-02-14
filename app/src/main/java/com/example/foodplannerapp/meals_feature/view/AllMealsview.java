package com.example.foodplannerapp.meals_feature.view;

import com.example.foodplannerapp.models.Meal;

import java.util.List;

public interface AllMealsview {
    public void showData(List<Meal> meals);
    public void showErrorMessage(String errorMessage);
}
