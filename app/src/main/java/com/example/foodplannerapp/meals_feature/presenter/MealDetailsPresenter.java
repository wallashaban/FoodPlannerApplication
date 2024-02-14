package com.example.foodplannerapp.meals_feature.presenter;

import com.example.foodplannerapp.models.Plan;

public interface MealDetailsPresenter {
    public void getMealByID(String id);
    public void addMealToPlan(Plan plan);
}
