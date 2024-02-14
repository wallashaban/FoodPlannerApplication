package com.example.foodplannerapp.meals_feature.view;

import com.example.foodplannerapp.models.Meal;

public interface RandomMealView {
    public void showData(Meal meal);
    public void showErrorMessage(String errorMessage);
}
