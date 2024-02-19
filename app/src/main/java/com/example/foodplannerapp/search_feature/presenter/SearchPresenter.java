package com.example.foodplannerapp.search_feature.presenter;

import com.example.foodplannerapp.models.AreaSearchParameters;
import com.example.foodplannerapp.models.CategorySearchParameters;
import com.example.foodplannerapp.models.IngredientSearchParameters;

public interface SearchPresenter {
    public void getAllCategories();

    public void searchByMealName();

    public void getAllAreas();

    public void getAllIngredients();

    public void searchByCategory(CategorySearchParameters parameters);

    public void searchByIngredient(IngredientSearchParameters parameters);

    public void searchByCountry(AreaSearchParameters parameters);
}
