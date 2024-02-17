package com.example.foodplannerapp.search_feature.presenter;

import android.util.Log;

import com.example.foodplannerapp.Repository.RemoteRepository;
import com.example.foodplannerapp.Repository.RemoteRepositoryImpl;
import com.example.foodplannerapp.models.Area;
import com.example.foodplannerapp.models.AreaSearchParameters;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.CategorySearchParameters;
import com.example.foodplannerapp.models.Ingredient;
import com.example.foodplannerapp.models.IngredientSearchParameters;
import com.example.foodplannerapp.network.AreaNetworkCallBAck;
import com.example.foodplannerapp.network.CategoriesNetworkCallBAck;
import com.example.foodplannerapp.network.IngredientsNetworkCallBAck;
import com.example.foodplannerapp.search_feature.view.CategorySearchAdapter;
import com.example.foodplannerapp.search_feature.view.SearchView;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class SearchPresenterImpl implements SearchPresenter,
        CategoriesNetworkCallBAck, AreaNetworkCallBAck,
        IngredientsNetworkCallBAck {
    private RemoteRepository repository;
    private SearchView view;
    private static SearchPresenterImpl instance = null;
    private SearchPresenterImpl(RemoteRepository repository,SearchView view)
    {
        this.repository = repository;
        this.view = view;
    }
    public static synchronized SearchPresenterImpl getInstance(RemoteRepository repository,SearchView view)
    {
        if (instance == null) {
            instance = new SearchPresenterImpl(repository,view);
        }else{
            instance.view = view;
        }
        return instance;
    }

    @Override
    public void getAllCategories() {
        repository.getAllCategoriesNetworkCallBack(this);
    }

    @Override
    public void searchByMealName() {

    }

    @Override
    public void getAllAreas() {
        repository.getAllAreasNetworkCallBack(this);
    }

    @Override
    public void getAllIngredients() {
        repository.getAllIngredientsNetworkCallBack(this);
    }

    @Override
    public void searchByCategory(CategorySearchParameters parameters) {
        Observable<Category> namesObservable = Observable.fromIterable(parameters. getCategories());
       parameters.getFilteredCategories().clear();
        namesObservable.filter(val->val.getCategory().toLowerCase().contains(
               parameters. getSequence())).subscribe(
                item->{
                    Log.i("TAG", "onTextChanged: "+item);
                   parameters. getFilteredCategories().add(item);
                },
                error-> Log.i("TAG", "onTextChanged: "),
                () -> {
                   parameters.getAdapter().setCategories(parameters. getFilteredCategories());
                  parameters. getAdapter().notifyDataSetChanged();
                }

        );
    }

    @Override
    public void searchByIngredient(IngredientSearchParameters parameters) {
        Observable<Ingredient> ingredientObservable = Observable.fromIterable(parameters. getIngredients());
        parameters.getFilteredIngredients().clear();
        ingredientObservable.filter(val->val.getIngredient().toLowerCase().contains(
                parameters. getSequence())).subscribe(
                item->{
                    Log.i("TAG", "onTextChanged: "+item);
                    parameters. getFilteredIngredients().add(item);
                },
                error-> Log.i("TAG", "onTextChanged: "),
                () -> {
                    parameters.getAdapter().setIngredients(parameters. getFilteredIngredients());
                    parameters. getAdapter().notifyDataSetChanged();
                }

        );
    }

    @Override
    public void searchByCountry(AreaSearchParameters parameters) {
        Observable<Area> categoryObservable = Observable.fromIterable(parameters. getAreas());
        parameters.getFilteredAreea().clear();
        categoryObservable.filter(val->val.getArea().toLowerCase().contains(
                parameters. getSequence())).subscribe(
                item->{
                    Log.i("TAG", "onTextChanged: "+item);
                    parameters. getFilteredAreea().add(item);
                },
                error-> Log.i("TAG", "onTextChanged: "),
                () -> {
                    parameters.getAdapter().setAreas(parameters. getFilteredAreea());
                    parameters. getAdapter().notifyDataSetChanged();
                }

        );
    }

    @Override
    public void onAreasSuccessResult(List<Area> areas) {
        view.showAreasData(areas);
    }

    @Override
    public void onAreasFailureResult(String errorMessage) {
        view.showAreasError(errorMessage);
    }

    @Override
    public void onCategoriesSuccessResult(List<Category> categories) {
        view.showCategoriessData(categories);
    }

    @Override
    public void onCategoriesFailureResult(String errorMessage) {
        view.showCategoriesError(errorMessage);
    }

    @Override
    public void onIngredientsSuccessResult(List<Ingredient> ingredients) {
        view.showIngredientsData(ingredients);
    }

    @Override
    public void onIngredientsFailureResult(String errorMessage) {
        view.showIngredientsError(errorMessage);
    }
}
