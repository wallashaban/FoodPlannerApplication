package com.example.foodplannerapp.models;

import com.example.foodplannerapp.search_feature.view.CategorySearchAdapter;

import java.util.List;

public class CategorySearchParameters {
    List<Category> categories;
    List<Category> filteredCategories;
    CategorySearchAdapter adapter;
    CharSequence sequence;

    public CategorySearchParameters(List<Category> categories,
                                    List<Category> filteredCategories,
                                    CategorySearchAdapter adapter,
                                    CharSequence sequence) {
        this.categories = categories;
        this.filteredCategories = filteredCategories;
        this.adapter = adapter;
        this.sequence = sequence;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setFilteredCategories(List<Category> filteredCategories) {
        this.filteredCategories = filteredCategories;
    }

    public void setAdapter(CategorySearchAdapter adapter) {
        this.adapter = adapter;
    }

    public void setSequence(CharSequence sequence) {
        this.sequence = sequence;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Category> getFilteredCategories() {
        return filteredCategories;
    }

    public CategorySearchAdapter getAdapter() {
        return adapter;
    }

    public CharSequence getSequence() {
        return sequence;
    }
}
