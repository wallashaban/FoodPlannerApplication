package com.example.foodplannerapp.meals_feature.view;

import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.DialyMeal;
import com.example.foodplannerapp.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

public interface HomeView {
    public void setDailyMeal(Maybe<DialyMeal> meal);
    public void insertDalyMeal(DialyMeal meal);
    public void removeDalyMeal();
    public Maybe<DialyMeal> getDailyMeal(String date);
    public void showMealsData(List<Meal> meals);

    public void showMealsErrorMessage(String errorMessage);

    public void showCategoriesData(List<Category> categories);

    public void showCategoriesErrorMessage(String errorMessage);

    public void showRandomMealData(Meal meals);
    public void onNetworkFailure(String errorMessage);

    public void showRandomMealErrorMessage(String errorMessage);

}
