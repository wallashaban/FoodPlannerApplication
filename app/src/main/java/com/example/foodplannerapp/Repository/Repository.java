package com.example.foodplannerapp.Repository;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.AreaNetworkCallBAck;
import com.example.foodplannerapp.network.CategoriesNetworkCallBAck;
import com.example.foodplannerapp.network.IngredientsNetworkCallBAck;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;
import com.example.foodplannerapp.network.RandomMealNetworkCallBAck;

import java.util.List;

public interface Repository {

    LiveData<List<Plan>> getAllPlans();
    void addPlan(Plan plan);
    void removePlan(Plan plan);
    void updatePlan(Plan plan);
    LiveData<Plan> getPlaneByDate(String date);
    LiveData<List<Meal>> getAllFavMeals();
    LiveData<Meal>getFavMealById(String id);

    void addMealToFavourites(Meal meal);

    void removeMealFromFavourites(Meal meal);

    public void getRandomMealsNetworkCallBack(RandomMealNetworkCallBAck networkCallBAck);

    public void getMealByIdNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String id);

    public void searchMealByNameNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String name);

    public void filterMealByMainIngredientNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String ingredient);

    public void filterMealByCategoryNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String categoey);

    public void searchMealByFirstLetterNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String firstLetter);

    public void getAllCategoriesNetworkCallBack(CategoriesNetworkCallBAck networkCallBAck);

    public void getAllAreasNetworkCallBack(AreaNetworkCallBAck networkCallBAck);

    public void getAllIngredientsNetworkCallBack(IngredientsNetworkCallBAck networkCallBAck);
}
