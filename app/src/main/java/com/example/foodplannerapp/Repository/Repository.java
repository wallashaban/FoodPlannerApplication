package com.example.foodplannerapp.Repository;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.models.DialyMeal;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.AreaNetworkCallBAck;
import com.example.foodplannerapp.network.CategoriesNetworkCallBAck;
import com.example.foodplannerapp.network.IngredientsNetworkCallBAck;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;
import com.example.foodplannerapp.network.RandomMealNetworkCallBAck;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

public interface Repository {

    public void insertDalyMeal(DialyMeal meal);

    public void removeDalyMeal();

    public Maybe<DialyMeal> getDailyMeal(String date);

    Flowable<List<Plan>> getAllPlans();

    void addPlan(Plan plan);

    void removePlan(Plan plan);

    void updatePlan(Plan plan);

    Flowable<Plan> getPlaneByDate(String date);

    Flowable<List<Meal>> getAllFavMeals();

    Flowable<Meal> getFavMealById(String id);

    void addMealToFavourites(Meal meal);

    void removeMealFromFavourites(Meal meal);

    public void getRandomMealsNetworkCallBack(RandomMealNetworkCallBAck networkCallBAck);

    public void getMealByIdNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String id);

    public void searchMealByNameNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String name);

    public void filterMealByMainIngredientNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String ingredient);

    public void filterMealByCategoryNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String categoey);

    public void searchMealByFirstLetterNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String firstLetter);

    public void getAllCategoriesNetworkCallBack(CategoriesNetworkCallBAck networkCallBAck);

    public void filterMealByAreaNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String area);

    public void getAllAreasNetworkCallBack(AreaNetworkCallBAck networkCallBAck);

    public void getAllIngredientsNetworkCallBack(IngredientsNetworkCallBAck networkCallBAck);
}
