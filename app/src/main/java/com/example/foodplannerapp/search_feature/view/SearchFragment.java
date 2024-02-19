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
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
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
        SearchView, OnAreaClickListener, OnIngredientClickListener {


    private ProgressBar progressBar;
    private LottieAnimationView lottieAnimationView;
    private CategorySearchAdapter categoryAdapter;
    private AreaSearchAdapter areaSearchAdapter;
    private IngredientSearchAdapter ingredientSearchAdapter;
    private List<Category> categories;
    private GridLayoutManager categoryManager;
    private GridLayoutManager areaManager;
    private GridLayoutManager ingredientManager;
    private RecyclerView searchCategoryRecyclerView;
    private RecyclerView searchIngredientRecyclerView;
    private RecyclerView searchCountryRecyclerView;
    private SearchPresenter presenter;
    private TextInputEditText search;
    private ChipGroup chipGroup;
    private Chip category;
    private Chip meal;
    private Chip ingredient;
    private List<Category> filteredCategories;
    private List<Area> filteredAreas;
    private List<Ingredient> filteredIngredients;
    private List<Area> areas;
    private List<Ingredient> ingredients;
    private Chip contry;
    private LottieAnimationView noSearch;
    private int chipNumber;


    public SearchFragment() {}


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
                .getInstance(RemoteDataSourceImpl.getInstance()), this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.searchProgressBar);
        lottieAnimationView = view.findViewById(R.id.no_internet_animation_search);
        noSearch = view.findViewById(R.id.noData);
        search = view.findViewById(R.id.search);
        chipGroup = view.findViewById(R.id.chipGroup);
        category = view.findViewById(R.id.categories);
        contry = view.findViewById(R.id.countries);
        meal = view.findViewById(R.id.meal);
        ingredient = view.findViewById(R.id.ingredientSearch);

        searchCountryRecyclerView = view.findViewById(R.id.searchAreaRecyclerView);
        searchCategoryRecyclerView = view.findViewById(R.id.searchCategoryRecyclerView);
        searchIngredientRecyclerView = view.findViewById(R.id.searchIngredientRecyclerView);
        categoryAdapter = new CategorySearchAdapter(getContext(), categories, this);
        areaSearchAdapter = new AreaSearchAdapter(getContext(), areas, this);
        ingredientSearchAdapter = new IngredientSearchAdapter(getContext(), ingredients, this);
        categoryManager = new GridLayoutManager(getContext(), 2);
        categoryManager.setOrientation(LinearLayoutManager.VERTICAL);
        areaManager = new GridLayoutManager(getContext(), 2);
        areaManager.setOrientation(LinearLayoutManager.VERTICAL);
        ingredientManager = new GridLayoutManager(getContext(), 2);
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
                if (categories.isEmpty()) {
                    noSearch.setVisibility(View.INVISIBLE);
                    presenter.getAllCategories();
                    progressBar.setVisibility(View.VISIBLE);
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);
                searchCategoryRecyclerView.setVisibility(View.VISIBLE);
                searchIngredientRecyclerView.setVisibility(View.INVISIBLE);
                searchCountryRecyclerView.setVisibility(View.INVISIBLE);
            }
        });
        contry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipNumber = 1;
                if (areas.isEmpty()) {
                    noSearch.setVisibility(View.INVISIBLE);
                    presenter.getAllAreas();
                    progressBar.setVisibility(View.VISIBLE);
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);
                searchCategoryRecyclerView.setVisibility(View.INVISIBLE);
                searchIngredientRecyclerView.setVisibility(View.INVISIBLE);
                searchCountryRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipNumber = 2;
                if (ingredients.isEmpty()) {
                    noSearch.setVisibility(View.INVISIBLE);
                    presenter.getAllIngredients();
                    progressBar.setVisibility(View.VISIBLE);
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);
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
                switch (chipNumber) {
                    case 0:
                        presenter.searchByCategory(new CategorySearchParameters(categories, filteredCategories,
                                categoryAdapter, s));
                        break;
                    case 1:
                        presenter.searchByCountry(new AreaSearchParameters(areas, filteredAreas,
                                areaSearchAdapter, s));
                        break;
                    case 2:
                        presenter.searchByIngredient(new IngredientSearchParameters(ingredients, filteredIngredients,
                                ingredientSearchAdapter, s));
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
        progressBar.setVisibility(View.INVISIBLE);
        lottieAnimationView.setVisibility(View.INVISIBLE);
        ingredientSearchAdapter.setIngredients(ingredients);
        ingredientSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showIngredientsError(String errorMessage) {

        if (errorMessage.equals("No internet connection")) {
            progressBar.setVisibility(View.INVISIBLE);
            Log.i("TAG", "showRandomMealErrorMessage: value");
            lottieAnimationView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showCategoriessData(List<Category> categories) {
        this.categories = categories;
        progressBar.setVisibility(View.INVISIBLE);
        lottieAnimationView.setVisibility(View.INVISIBLE);
        categoryAdapter.setCategories(categories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoriesError(String errorMessage) {
        if (errorMessage.equals("No internet connection")) {
            progressBar.setVisibility(View.INVISIBLE);
            Log.i("TAG", "showRandomMealErrorMessage: value");
            lottieAnimationView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showAreasData(List<Area> areas) {
        this.areas = areas;
        lottieAnimationView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        areaSearchAdapter.setAreas(areas);
        areaSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAreasError(String errorMessage) {
        if (errorMessage.equals("No internet connection")) {
            progressBar.setVisibility(View.INVISIBLE);
            Log.i("TAG", "showRandomMealErrorMessage: value");
            lottieAnimationView.setVisibility(View.VISIBLE);
        }
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