package com.example.foodplannerapp.favourites_feature.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapp.database.FavouritesLocalDataSourceImpl;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.favourites_feature.presenter.FavouritesPresenter;
import com.example.foodplannerapp.favourites_feature.presenter.FavouritesPresenterImpl;
import com.example.foodplannerapp.favourites_feature.repository.FavouritesRepositoryImpl;
import com.example.foodplannerapp.meals_feature.view.OnFavClickListener;
import com.example.foodplannerapp.meals_feature.view.OnMealClickListener;
import com.example.foodplannerapp.models.Meal;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment implements FavouritesView, OnFavClickListener, OnMealClickListener {


    FavouritesPresenter presenter;
    GridLayoutManager favManager;

    RecyclerView favRecyclerView;

    List<Meal> meals;
    FavouritesAdapter favAdapter;
    private final static String TAG = "HomeFragment";

    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meals = new ArrayList<>();
        presenter = FavouritesPresenterImpl.getInstance(

                FavouritesRepositoryImpl.getInstance(
                        FavouritesLocalDataSourceImpl.getInstance(getContext())
                ), this
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favRecyclerView = view.findViewById(R.id.favsRecyclerView);
        favAdapter = new FavouritesAdapter(getContext(), meals, this, this);
        favManager = new GridLayoutManager(getContext(),2);
        favManager.setOrientation(LinearLayoutManager.VERTICAL);
        favRecyclerView.setLayoutManager(favManager);
        favRecyclerView.setAdapter(favAdapter);
        Log.i(TAG, "onViewCreated: "+meals.size());
        //if(meals.isEmpty())
            presenter.getAllFavouritesMeals();
    }

    @Override
    public void setFavouritesList(LiveData<List<Meal>> meals) {
        Log.i(TAG, "setFavouritesList: " + meals.toString());
        meals.observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                FavouritesFragment.this.meals = meals;
                favAdapter.setMeals(meals);
                Log.i(TAG, "setFavouritesList: " + meals.size());
                favAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void OnFavClickListener(Meal meal) {
       presenter.removeMealFromFavourites(meal);
    }



    @Override
    public void OnMealClickListener(String id,View view) {

    }
}