package com.example.foodplannerapp.auth_feature.view;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

public interface LoginView {
    public void showErrorMessage(String errorMessage);
    public void showData();
    public void showPlansErrorMessage(String errorMessage);
    public void showPlansData(List<Plan> plans);
    public void showMealsErrorMessage(String errorMessage);
    public void showMealsData(List<Meal> meals);
}
