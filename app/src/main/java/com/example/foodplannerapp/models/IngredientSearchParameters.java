package com.example.foodplannerapp.models;

import com.example.foodplannerapp.search_feature.view.CategorySearchAdapter;
import com.example.foodplannerapp.search_feature.view.IngredientSearchAdapter;

import java.util.List;

public class IngredientSearchParameters {
    List<Ingredient> ingredients;
    List<Ingredient> filteredIngredients;
    IngredientSearchAdapter adapter;

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setFilteredIngredients(List<Ingredient> filteredIngredients) {
        this.filteredIngredients = filteredIngredients;
    }

    public void setAdapter(IngredientSearchAdapter adapter) {
        this.adapter = adapter;
    }

    public void setSequence(CharSequence sequence) {
        this.sequence = sequence;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Ingredient> getFilteredIngredients() {
        return filteredIngredients;
    }

    public IngredientSearchAdapter getAdapter() {
        return adapter;
    }

    public CharSequence getSequence() {
        return sequence;
    }

    public IngredientSearchParameters(List<Ingredient> ingredients, List<Ingredient> filteredIngredients, IngredientSearchAdapter adapter, CharSequence sequence) {
        this.ingredients = ingredients;
        this.filteredIngredients = filteredIngredients;
        this.adapter = adapter;
        this.sequence = sequence;
    }

    CharSequence sequence;


}
