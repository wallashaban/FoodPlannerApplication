package com.example.foodplannerapp.search_feature.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Repository.RemoteRepositoryImpl;
import com.example.foodplannerapp.meals_feature.view.CategoryAdapter;
import com.example.foodplannerapp.meals_feature.view.OnCategoryClickListener;
import com.example.foodplannerapp.models.Area;
import com.example.foodplannerapp.models.AreaSearchParameters;
import com.example.foodplannerapp.models.Category;
import com.example.foodplannerapp.models.CategorySearchParameters;
import com.example.foodplannerapp.models.Ingredient;
import com.example.foodplannerapp.models.IngredientSearchParameters;
import com.example.foodplannerapp.network.RemoteDataSourceImpl;
import com.example.foodplannerapp.search_feature.presenter.SearchPresenter;
import com.example.foodplannerapp.search_feature.presenter.SearchPresenterImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;


public class SearchFragment extends Fragment implements OnCategoryClickListener,
SearchView,OnAreaClickListener,OnIngredientClickListener{


    CategorySearchAdapter categoryAdapter;
    AreaSearchAdapter areaSearchAdapter;
    IngredientSearchAdapter ingredientSearchAdapter;
    List<Category> categories;
    GridLayoutManager categoryManager;
    GridLayoutManager areaManager;
    GridLayoutManager ingredientManager;
    RecyclerView searchCategoryRecyclerView;
    RecyclerView searchIngredientRecyclerView;
    RecyclerView searchCountryRecyclerView;
    SearchPresenter presenter;
    TextInputEditText search;
    ChipGroup chipGroup;
    Chip category;
    Chip meal;
    Chip ingredient;
    List<Category> filteredCategories;
    List<Area> filteredAreas;
    List<Ingredient> filteredIngredients;
    List<Area> areas;
    List<Ingredient> ingredients;
    Chip contry;
    int chipNumber ;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = new ArrayList<>();
        filteredCategories = new ArrayList<>();
        areas = new ArrayList<>();
        filteredAreas = new ArrayList<>();
        ingredients = new ArrayList<>();
        filteredIngredients = new ArrayList<>();
        presenter = SearchPresenterImpl.getInstance(RemoteRepositoryImpl
                .getInstance(RemoteDataSourceImpl.getInstance()),this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search = view.findViewById(R.id.search);
        chipGroup = view.findViewById(R.id.chipGroup);
        category = view.findViewById(R.id.categories);
        contry = view.findViewById(R.id.countries);
        meal = view.findViewById(R.id.meal);
        ingredient = view.findViewById(R.id.ingredientSearch);

        searchCountryRecyclerView = view.findViewById(R.id.searchAreaRecyclerView);
        searchCategoryRecyclerView = view.findViewById(R.id.searchCategoryRecyclerView);
        searchIngredientRecyclerView = view.findViewById(R.id.searchIngredientRecyclerView);
        categoryAdapter = new CategorySearchAdapter(getContext(), categories,this);
        areaSearchAdapter = new AreaSearchAdapter(getContext(), areas,this);
        ingredientSearchAdapter = new IngredientSearchAdapter(getContext(), ingredients,this);
        categoryManager = new GridLayoutManager(getContext(),2);
        categoryManager.setOrientation(LinearLayoutManager.VERTICAL);
        areaManager = new GridLayoutManager(getContext(),2);
        areaManager.setOrientation(LinearLayoutManager.VERTICAL);
        ingredientManager = new GridLayoutManager(getContext(),2);
        ingredientManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchCategoryRecyclerView.setLayoutManager(categoryManager);
        searchCategoryRecyclerView.setAdapter(categoryAdapter);
        searchIngredientRecyclerView.setLayoutManager(ingredientManager);
        searchIngredientRecyclerView.setAdapter(ingredientSearchAdapter);

        searchCountryRecyclerView.setLayoutManager(areaManager);
        searchCountryRecyclerView.setAdapter(areaSearchAdapter);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chipNumber = 0;
                presenter.getAllCategories();
                searchCategoryRecyclerView.setVisibility(View.VISIBLE);
                searchIngredientRecyclerView.setVisibility(View.INVISIBLE);
                searchCountryRecyclerView.setVisibility(View.INVISIBLE);
            }
        });
        contry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipNumber = 1;
                presenter.getAllAreas();
                searchCategoryRecyclerView.setVisibility(View.INVISIBLE);
                searchIngredientRecyclerView.setVisibility(View.INVISIBLE);
                searchCountryRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipNumber = 2;
                presenter.getAllIngredients();
                searchCategoryRecyclerView.setVisibility(View.INVISIBLE);
                searchIngredientRecyclerView.setVisibility(View.VISIBLE);
                searchCountryRecyclerView.setVisibility(View.INVISIBLE);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("TAG", "onTextChanged: ");
                switch (chipNumber)
                {
                    case 0:
                        presenter.searchByCategory(new CategorySearchParameters(categories,filteredCategories,
                                categoryAdapter,s));
                        break;
                    case 1:
                        presenter.searchByCountry(new AreaSearchParameters(areas,filteredAreas,
                                areaSearchAdapter,s));
                        break;
                    case 2:
                        presenter.searchByIngredient(new IngredientSearchParameters(ingredients,filteredIngredients,
                                ingredientSearchAdapter,s));
                        break;
                }


            }



            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    @Override
    public void onCategoryClickListener(String category, View view) {
        SearchFragmentDirections.ActionSearchFragment2ToAllMealsFragment3 action =
                SearchFragmentDirections.actionSearchFragment2ToAllMealsFragment3();
        action.setCategory(category);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void showIngredientsData(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        ingredientSearchAdapter.setIngredients(ingredients);
        ingredientSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showIngredientsError(String errorMessage) {

    }

    @Override
    public void showCategoriessData(List<Category> categories) {
        this.categories = categories;
        categoryAdapter.setCategories(categories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoriesError(String errorMessage) {

    }

    @Override
    public void showAreasData(List<Area> areas) {
        this.areas = areas;
        areaSearchAdapter.setAreas(areas);
        areaSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAreasError(String errorMessage) {

    }

    @Override
    public void onAreaClickLitener(String area, View view) {
        SearchFragmentDirections.ActionSearchFragment2ToAllMealsFragment3 action =
                SearchFragmentDirections.actionSearchFragment2ToAllMealsFragment3();
        action.setArea(area);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onIngredientClickListener(String ingredient, View view) {
        SearchFragmentDirections.ActionSearchFragment2ToAllMealsFragment3 action =
                SearchFragmentDirections.actionSearchFragment2ToAllMealsFragment3();
        action.setIngredient(ingredient);
        Navigation.findNavController(view).navigate(action);
    }
}