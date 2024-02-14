package com.example.foodplannerapp.meals_feature.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.Repository.RepositoryImpl;
import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
import com.example.foodplannerapp.meals_feature.presenter.AllMealsPresenter;
import com.example.foodplannerapp.meals_feature.presenter.AllMealsPresenterImpl;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.network.RemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class AllMealsFragment extends Fragment implements AllMealsview,OnMealClickListener,OnFavClickListener{


    private static final String TAG = "AllMealsFragment";
    AllMealsPresenter presenter;
    MealAdapter categoryMealsAdapter;
    List<Meal> meals;
    List<Meal> categoryMeals;

    MealAdapter mealsAdapter;
    RecyclerView mealsRecyclerView;
    RecyclerView categoryRecyclerView;
    GridLayoutManager mealsManager;
    GridLayoutManager categoryManager;
    static  int count =0;


    public AllMealsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count++;
        meals = new ArrayList<>();
        categoryMeals = new ArrayList<>();
        presenter = AllMealsPresenterImpl.getInstance(

                this, RepositoryImpl.getInstance(
                        RemoteDataSourceImpl.getInstance(),
                        FavouritesLocalDataSourceImpl.getInstance(getContext())
                )
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealsRecyclerView = view.findViewById(R.id.allMealsRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.allMealsRecyclerView);
        mealsAdapter = new MealAdapter(getContext(), meals, this, this);
        mealsManager = new GridLayoutManager(getContext(),2);
        categoryManager = new GridLayoutManager(getContext(),2);
        categoryMealsAdapter = new MealAdapter(getContext(),categoryMeals,this,this);
        mealsManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryManager.setOrientation(RecyclerView.VERTICAL);

        if(count == 2)
        {
            categoryRecyclerView.setLayoutManager(categoryManager);
            categoryRecyclerView.setAdapter(categoryMealsAdapter);
        }
        mealsRecyclerView.setLayoutManager(mealsManager);
        mealsRecyclerView.setAdapter(mealsAdapter);
        Log.i(TAG, "onViewCreated: meals  :: Category"+meals.size());
        String category = AllMealsFragmentArgs.fromBundle(getArguments()).getCategory();
        //if (meals.isEmpty())
            if(category!=null)
            {
                presenter.filterMEalByCategory(category);
            }

    }

    @Override
    public void showData(List<Meal> meals) {
        Log.i(TAG, "showData: category" + meals.size()+"  "+count);

            mealsAdapter.setMeals(meals);
            mealsAdapter.notifyDataSetChanged();
            //List<MealIngredients> ingredients = extractIngredients(meals.get(0));
            //Log.i(TAG, "showData: Ingredients" + ingredients.size());

    }


    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnFavClickListener(Meal meal) {

        presenter.addMealToFavourites(meal);
    }
    @Override
    public void OnMealClickListener(String id,View view) {

    }
}