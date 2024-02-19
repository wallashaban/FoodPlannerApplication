package com.example.foodplannerapp.Repository;

import com.example.foodplannerapp.network.AreaNetworkCallBAck;
import com.example.foodplannerapp.network.CategoriesNetworkCallBAck;
import com.example.foodplannerapp.network.IngredientsNetworkCallBAck;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;
import com.example.foodplannerapp.network.RandomMealNetworkCallBAck;
import com.example.foodplannerapp.network.RemoteDataSource;

public class RemoteRepositoryImpl implements RemoteRepository {
    RemoteDataSource remoteDataSource;
    private static RemoteRepositoryImpl instance = null;

    private RemoteRepositoryImpl(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static synchronized RemoteRepositoryImpl getInstance(
            RemoteDataSource remoteDataSource) {
        if (instance == null) {
            instance = new RemoteRepositoryImpl(remoteDataSource);
        }
        return instance;
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
