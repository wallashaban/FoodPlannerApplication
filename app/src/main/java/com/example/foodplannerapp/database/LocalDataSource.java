package com.example.foodplannerapp.database;

import com.example.foodplannerapp.models.DialyMeal;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

public interface LocalDataSource {

    void insertDailyMeal(DialyMeal meal);

    void removeDailyMeal();

    Maybe<DialyMeal> getDailyMeal(String date);


    public Flowable<List<Meal>> getAllFavMeals();

    public Flowable<Meal> getFavMealById(String id);


    public void addMealToFavourites(Meal meal);

    public void removeMealFromFavourites(Meal meal);

    ///////////////////////////////////////////////////
    Flowable<List<Plan>> getAllPlans();

    void addPlan(Plan plan);

    void removePlan(Plan plan);

    void updatePlan(Plan plan);

    Flowable<Plan> getPlaneByDate(String date);

    public void deleteAllFavMeals();

    public void deleteAllPlans();

    public void insertAllFavouries(List<Meal> meals);

    public void insertAllPlans(List<Plan> plans);
}
