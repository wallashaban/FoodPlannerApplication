package com.example.foodplannerapp.search_feature.view;

import com.example.foodplannerapp.models.Area;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.Ingredient;

import java.util.List;

public interface SearchView {
    public void showIngredientsData(List<Ingredient> ingredients);
    public void showIngredientsError(String errorMessage);

    public void showCategoriessData(List<Category> categories);
    public void showCategoriesError(String errorMessage);

    public void showAreasData(List<Area> areas);
    public void showAreasError(String errorMessage);
}
