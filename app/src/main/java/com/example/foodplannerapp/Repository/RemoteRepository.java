package com.example.foodplannerapp.Repository;

import com.example.foodplannerapp.network.AreaNetworkCallBAck;
import com.example.foodplannerapp.network.CategoriesNetworkCallBAck;
import com.example.foodplannerapp.network.IngredientsNetworkCallBAck;
import com.example.foodplannerapp.network.MealsNetworkCallBAck;
import com.example.foodplannerapp.network.RandomMealNetworkCallBAck;

public interface RemoteRepository {
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
