package com.example.foodplannerapp.meals_feature.presenter;

import com.example.foodplannerapp.Repository.Repository;
import com.example.foodplannerapp.meals_feature.view.CategoryView;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.network.CategoriesNetworkCallBAck;

import java.util.List;

public class CategoryPresenterImpl implements CategoryPresenter , CategoriesNetworkCallBAck {
    private Repository repository;
    private CategoryView view;

    private static CategoryPresenterImpl instance;

    private CategoryPresenterImpl(CategoryView view,Repository repository){
        this.view = view;
        this.repository = repository;
    }
    public static CategoryPresenterImpl getInstance(CategoryView view,Repository repository)
    {
        if(instance == null)
        {
            instance = new CategoryPresenterImpl(view,repository);
        }
        return instance;
    }

    @Override
    public void getAllCategories() {
        repository.getAllCategoriesNetworkCallBack(this);
    }

    @Override
    public void onCategoriesSuccessResult(List<Category> categories) {
        view.showData(categories);
    }

    @Override
    public void onCategoriesFailureResult(String errorMessage) {
        view.showErrorMessage(errorMessage);
    }
}
