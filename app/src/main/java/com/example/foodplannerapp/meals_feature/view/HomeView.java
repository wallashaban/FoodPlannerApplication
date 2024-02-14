package com.example.foodplannerapp.meals_feature.view;

import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.Meal;

import java.util.List;

public interface HomeView {
    public void showMealsData(List<Meal> meals);

    public void showMealsErrorMessage(String errorMessage);

    public void showCategoriesData(List<Category> categories);

    public void showCategoriesErrorMessage(String errorMessage);

    public void showRandomMealData(Meal meals);

    public void showRandomMealErrorMessage(String errorMessage);

}
