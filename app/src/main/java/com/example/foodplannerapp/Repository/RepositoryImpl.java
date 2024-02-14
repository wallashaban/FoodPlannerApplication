package com.example.foodplannerapp.Repository;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.database.FavouritesLocalDataSource;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.Plan;
import com.example.foodplannerapp.network.AreaNetworkCallBAck;
import com.example.foodplannerapp.network.CategoriesNetworkCallBAck;
import com.example.foodplannerapp.network.IngredientsNetworkCallBAck;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;
import com.example.foodplannerapp.network.RandomMealNetworkCallBAck;
import com.example.foodplannerapp.network.RemoteDataSource;

import java.util.List;

public class RepositoryImpl implements Repository {

    RemoteDataSource remoteDataSource;
    FavouritesLocalDataSource localDataSource;

    private static RepositoryImpl instance = null;

    private RepositoryImpl(RemoteDataSource remoteDataSource, FavouritesLocalDataSource
            localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static synchronized RepositoryImpl getInstance(
            RemoteDataSource remoteDataSource, FavouritesLocalDataSource localDataSource) {
        if (instance == null) {
            instance = new RepositoryImpl(remoteDataSource, localDataSource);
        }
        return instance;
    }

    @Override
    public LiveData<List<Plan>> getAllPlans() {
        return localDataSource.getAllPlans();
    }

    @Override
    public void addPlan(Plan plan) {
        localDataSource.addPlan(plan);
    }

    @Override
    public void removePlan(Plan plan) {
        localDataSource.removePlan(plan);
    }

    @Override
    public void updatePlan(Plan plan) {
        localDataSource.updatePlan(plan);
    }

    @Override
    public LiveData<Plan> getPlaneByDate(String date) {
        return localDataSource.getPlaneByDate(date);
    }

    @Override
    public LiveData<List<Meal>> getAllFavMeals() {
        return localDataSource.getAllFavMeals();
    }

    @Override
    public LiveData<Meal> getFavMealById(String id) {
        return localDataSource.getFavMealById(id);
    }

    @Override
    public void addMealToFavourites(Meal meal) {
        localDataSource.addMealToFavourites(meal);
    }

    @Override
    public void removeMealFromFavourites(Meal meal) {
        localDataSource.removeMealFromFavourites(meal);
    }

    @Override
    public void getRandomMealsNetworkCallBack(RandomMealNetworkCallBAck networkCallBAck) {
        remoteDataSource.getRandomMealsNetworkCallBack(networkCallBAck);
    }

    @Override
    public void getMealByIdNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String id) {
        remoteDataSource.getMealByIdNetworkCallBack(networkCallBAck, id);
    }

    @Override
    public void searchMealByNameNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String name) {
        remoteDataSource.searchMealByNameNetworkCallBack(networkCallBAck, name);
    }

    @Override
    public void filterMealByMainIngredientNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String ingredient) {
        remoteDataSource.filterMealByMainIngredientNetworkCallBack(networkCallBAck, ingredient);
    }

    @Override
    public void filterMealByCategoryNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String categoey) {
        remoteDataSource.filterMealByCategoryNetworkCallBack(networkCallBAck, categoey);
    }

    @Override
    public void searchMealByFirstLetterNetworkCallBack(MealsNetworkCallBAck networkCallBAck, String firstLetter) {
        remoteDataSource.searchMealByFirstLetterNetworkCallBack(networkCallBAck, firstLetter);
    }

    @Override
    public void getAllCategoriesNetworkCallBack(CategoriesNetworkCallBAck networkCallBAck) {
        remoteDataSource.getAllCategoriesNetworkCallBack(networkCallBAck);
    }

    @Override
    public void getAllAreasNetworkCallBack(AreaNetworkCallBAck networkCallBAck) {
        remoteDataSource.getAllAreasNetworkCallBack(networkCallBAck);
    }

    @Override
    public void getAllIngredientsNetworkCallBack(IngredientsNetworkCallBAck networkCallBAck) {
        remoteDataSource.getAllIngredientsNetworkCallBack(networkCallBAck);
    }
}
