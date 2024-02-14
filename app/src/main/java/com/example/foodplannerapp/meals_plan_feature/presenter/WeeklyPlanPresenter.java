package com.example.foodplannerapp.meals_plan_feature.presenter;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

public interface WeeklyPlanPresenter {
    public void getAllPlansMeals();

    public void removePlan(Plan plan);
}
